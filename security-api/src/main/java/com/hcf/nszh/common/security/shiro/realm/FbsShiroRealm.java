package com.hcf.nszh.common.security.shiro.realm;

import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.common.security.shiro.config.ShiroProperties;
import com.hcf.nszh.common.security.shiro.utils.UserUtils;
import com.hcf.nszh.common.utils.Encodes;
import com.hcf.nszh.common.utils.StringUtils;
import com.hcf.nszh.common.constant.CacheConstant;
import com.hcf.nszh.common.constant.SystemConstant;
import com.hcf.nszh.provider.system.api.service.UserApiService;
import com.hcf.nszh.provider.system.api.vo.RoleVo;
import com.hcf.nszh.provider.system.api.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;

/**
 * @Author huangxiong
 * @Date 2019/6/26
 **/
@Slf4j
public class FbsShiroRealm extends AuthorizingRealm {

    public static final String CACHE_AUTH_INFO = "authInfo";

    @Autowired
    private UserApiService userApiService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ShiroProperties shiroProperties;

    public FbsShiroRealm() {
        this.setCachingEnabled(false);
    }

    /**
     * 获取权限授权信息，如果缓存中存在，则直接从缓存中获取，否则就重新获取， 登录成功后调用
     */
    @Override
    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            return null;
        }

        AuthorizationInfo info;

        info = (AuthorizationInfo) UserUtils.getCache(CACHE_AUTH_INFO);

        if (info == null) {
            info = doGetAuthorizationInfo(principals);
            if (info != null) {
                UserUtils.putCache(CACHE_AUTH_INFO, info);
            }
        }

        Principal principal = (Principal) getAvailablePrincipal(principals);
        //保存缓存有效
        UserUtils.keepCurrentUserCache(shiroProperties.getExpireIn(), principal);
        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("授权...");
        //获取登录用户名
        Principal principal = (Principal) getAvailablePrincipal(principalCollection);
        String name = principal.getLoginName();
        //查询用户名称
        ResponseVo<UserVo> userVoResponseVo = userApiService.getUserVoByLoginName(name);
        if (userVoResponseVo.getCode() != ErrorEnum.SUCCESS.getErrorCode()) {
            throw new AuthenticationException(userVoResponseVo.getError());
        }
        UserVo userVo = userVoResponseVo.getData();
        if (null == userVo) {
            return null;
        }
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (RoleVo role : userVo.getRoleList()) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
        }
        for (String permission : userVo.getPermissionList()) {
            //添加权限
            simpleAuthorizationInfo.addStringPermission(permission);
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        ResponseVo<UserVo> userVoResponseVo = userApiService.
                getUserVoByLoginName(userName);
        if (userVoResponseVo.getCode() != ErrorEnum.SUCCESS.getErrorCode()) {
            throw new AuthenticationException(userVoResponseVo.getError());
        }
        UserVo userVo = userVoResponseVo.getData();
        if (null == userVo) {
            return null;
        }
        if (userVo.getDelFlag().equals(SystemConstant.DEL_FLAG_DELETE)) {
            throw new AuthenticationException("该帐号已禁止登录.");
        }
        String loginFlag = userVo.getLoginFlag();
        if (Strings.isNotBlank(loginFlag)&& loginFlag.equals(SystemConstant.NO)) {
            //用户不允许后台登陆
            throw new AuthenticationException("用户不允许后台登录,请联系管理员");
        }

        if (userVo.getRoleList() == null || userVo.getRoleList().size() < 1 || userVo.getMenuVoList() == null || userVo.getMenuVoList().size() < 1) {
            throw new AuthenticationException("用户暂无可用角色或可用菜单权限，请联系管理员");
        }

        byte[] salt = Encodes.decodeHex(userVo.getPassword().substring(0, 16));
        return new SimpleAuthenticationInfo(new Principal(userVo, false),
                userVo.getPassword().substring(16), ByteSource.Util.bytes(salt), getName());
    }

    @Override
    protected void doClearCache(PrincipalCollection principals) {
        //退出，清除用户缓存
        Principal principal = (Principal) getAvailablePrincipal(principals);
        redisTemplate.delete(CacheConstant.USER_CACHE + CacheConstant.USER_CACHE_ID_
                + principal.getId());
        redisTemplate.delete(CacheConstant.USER_CACHE + CacheConstant.USER_CACHE_LOGIN_NAME_
                + principal.getLoginName());
        //清除token缓存
        redisTemplate.delete(CacheConstant.USER_CACHE + CacheConstant.USER_CACHE_USER_TOKEN_
                + principal.getLoginName());
        super.doClearCache(principals);
    }

    /**
     * 授权用户信息
     */
    public static class Principal implements Serializable {

        private static final long serialVersionUID = 1L;

        private String id; // 编号
        private String loginName; // 登录名
        private String name; // 姓名
        private boolean mobileLogin; // 是否手机登录
        private boolean admin;

        public Principal(UserVo user, boolean mobileLogin) {
            this.id = String.valueOf(user.getUserId());
            this.loginName = user.getLoginName();
            this.name = user.getName();
            this.mobileLogin = mobileLogin;
            this.setAdmin(user.isAdmin());
        }

        public String getId() {
            return id;
        }

        public String getLoginName() {
            return loginName;
        }

        public String getName() {
            return name;
        }

        public boolean isMobileLogin() {
            return mobileLogin;
        }


        /**
         * 获取SESSIONID
         */
        public String getSessionid() {
            try {
                return (String) UserUtils.getSession().getId();
            } catch (Exception e) {
                return "";
            }
        }

        @Override
        public String toString() {
            return id;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }

    }
}
