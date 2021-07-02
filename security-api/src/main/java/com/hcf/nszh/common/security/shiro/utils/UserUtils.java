package com.hcf.nszh.common.security.shiro.utils;


import com.alibaba.fastjson.JSON;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.common.utils.SpringContextUtils;
import com.hcf.nszh.common.constant.CacheConstant;
import com.hcf.nszh.common.exception.BusinessException;
import com.hcf.nszh.common.security.shiro.realm.FbsShiroRealm;
import com.hcf.nszh.provider.system.api.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.*;

/**
 * @author maruko
 * @Date 2019/6/29
 **/
@Slf4j
public class UserUtils {

    private static ExecutorService executorService = new ThreadPoolExecutor(5, 100,
            60L, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>());

    public static Session getSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null) {
                session = subject.getSession();
            }
            if (session != null) {
                return session;
            }
        } catch (InvalidSessionException e) {

        }
        return null;
    }

    public static Object getCache(String key) {
        return getCache(key, null);
    }

    public static Object getCache(String key, Object defaultValue) {
        Object obj = getSession().getAttribute(key);
        return obj == null ? defaultValue : obj;
    }

    public static void putCache(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static UserVo getUser() {
        FbsShiroRealm.Principal principal = getPrincipal();
        if (principal != null) {
            log.info("principal userId:{}", principal.getId());
            UserVo user = get(principal.getId());
            if (user != null) {
                return user;
            }
            log.info("no cache principal userId:{}", principal.getId());
            UserVo userVo = new UserVo();
            userVo.setUserId(Long.valueOf(principal.getId()));
            userVo.setLoginName(principal.getLoginName());
            userVo.setName(principal.getName());
            return userVo;
        } else {
            throw new BusinessException(ErrorEnum.USER_EMPTY);
        }
    }

    /**
     * 获取当前登录者对象
     *
     * @return
     */
    public static FbsShiroRealm.Principal getPrincipal() {
        try {
            Subject subject = SecurityUtils.getSubject();
            FbsShiroRealm.Principal principal = (FbsShiroRealm.Principal) subject.getPrincipal();
            if (principal != null) {
                return principal;
            }
        } catch (UnavailableSecurityManagerException e) {

        } catch (InvalidSessionException e) {

        }
        return null;
    }

    public static StringRedisTemplate stringRedisTemplate() {
        SpringContextUtils springContextUtils = new SpringContextUtils();
        StringRedisTemplate stringRedisTemplate = springContextUtils.
                getBean(StringRedisTemplate.class);
        return stringRedisTemplate;
    }

    public static UserVo get(String id) {
        StringRedisTemplate stringRedisTemplate = stringRedisTemplate();
        UserVo user = JSON.parseObject(stringRedisTemplate.opsForValue().get(CacheConstant.USER_CACHE +
                CacheConstant.USER_CACHE_ID_ + id), UserVo.class);
        return user;
    }

    public static void clearCache(UserVo userVo) {
        StringRedisTemplate stringRedisTemplate = stringRedisTemplate();
        stringRedisTemplate.delete(CacheConstant.USER_CACHE + CacheConstant.USER_CACHE_ID_
                + userVo.getUserId());
        stringRedisTemplate.delete(CacheConstant.USER_CACHE + CacheConstant.USER_CACHE_USER_ID_
                + userVo.getUserId());
        stringRedisTemplate.delete(CacheConstant.USER_CACHE + CacheConstant.USER_CACHE_LOGIN_NAME_
                + userVo.getLoginName());
        //清除token缓存
        stringRedisTemplate.delete(CacheConstant.USER_CACHE + CacheConstant.USER_CACHE_USER_TOKEN_
                + userVo.getLoginName());
    }

    /**
     * 保持当前用户的缓存过期时间
     *
     * @param expireIn
     * @param principal
     */
    public static void keepCurrentUserCache(long expireIn, FbsShiroRealm.Principal principal) {
        if (null == principal) {
            principal = getPrincipal();
        }
        //保存缓存有效
        executorService.submit(new KeepCurrentUserCacheTask(expireIn, principal));
    }

    @Slf4j
    static class KeepCurrentUserCacheTask implements Callable<String> {
        private long expireIn;
        private FbsShiroRealm.Principal principal;

        public KeepCurrentUserCacheTask(long expireIn, FbsShiroRealm.Principal principal) {
            this.expireIn = expireIn;
            this.principal = principal;
        }

        /**
         * 保持当前用户的缓存过期时间任务
         *
         * @return
         * @throws Exception
         */
        @Override
        public String call() {
            log.info("KeepCurrentUserCacheTask userId :{} start", principal.getId());
            StringRedisTemplate stringRedisTemplate = stringRedisTemplate();
            if (stringRedisTemplate.hasKey(CacheConstant.USER_CACHE +
                    CacheConstant.USER_CACHE_LOGIN_NAME_
                    + principal.getLoginName())) {
                stringRedisTemplate.expire(CacheConstant.USER_CACHE + CacheConstant.USER_CACHE_ID_
                        + principal.getId(), expireIn, TimeUnit.SECONDS);
                stringRedisTemplate.expire(CacheConstant.USER_CACHE + CacheConstant.USER_CACHE_LOGIN_NAME_
                        + principal.getLoginName(), expireIn, TimeUnit.SECONDS);
                //如果有token，则保持token的有效时间
                if (stringRedisTemplate.hasKey(CacheConstant.USER_CACHE + CacheConstant.USER_CACHE_USER_TOKEN_
                        + principal.getLoginName())) {
                    stringRedisTemplate.expire(CacheConstant.USER_CACHE + CacheConstant.USER_CACHE_USER_TOKEN_
                            + principal.getLoginName(), CacheConstant.USER_CACHE_USER_TOKE_EXPIRES, TimeUnit.SECONDS);
                }
            }
            log.info("KeepCurrentUserCacheTask userId :{} end", principal.getId());
            return "OK";
        }
    }
}
