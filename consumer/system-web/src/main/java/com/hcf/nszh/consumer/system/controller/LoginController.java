package com.hcf.nszh.consumer.system.controller;

import com.alibaba.fastjson.JSON;
import com.hcf.nszh.common.annotation.RequestLogging;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.constant.CacheConstant;
import com.hcf.nszh.common.constant.SystemConstant;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.common.exception.BusinessException;
import com.hcf.nszh.common.security.shiro.utils.UserUtils;
import com.hcf.nszh.common.utils.AesUtils;
import com.hcf.nszh.common.utils.StringUtils;
import com.hcf.nszh.consumer.system.dto.LoginDto;
import com.hcf.nszh.provider.system.api.service.UserApiService;
import com.hcf.nszh.provider.system.api.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author maruko
 * @Date 2019/6/26
 **/
@Api(value = "API - LoginController", description = "登录服务")
@RequestMapping(value = "auth")
@RestController
@Slf4j
@RefreshScope
public class LoginController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserApiService userApiService;

    /**
     * 管理登录
     */
    @ApiOperation("用户登录")
    @RequestLogging
    @PostMapping("/login")
    public ResponseVo<UserVo> loginPost(@Valid @RequestBody LoginDto loginDto,
                                        HttpServletRequest request) {
        log.info("userName:{},password:{}", loginDto.getUsername(), loginDto.getPassword());
        String sessionCaptcha = (String) SecurityUtils.getSubject().getSession().
                getAttribute(CaptchaController.KEY_CAPTCHA);
        //配合测试暂时 不启用登录验证码 验证判断。
        if (null == loginDto.getCaptcha() || !loginDto.getCaptcha().equalsIgnoreCase(sessionCaptcha)) {
            return new ResponseVo<>(ErrorEnum.USER_NO_CAPTCHA);
        }
        UsernamePasswordToken token = new UsernamePasswordToken(loginDto.getUsername(),
                AesUtils.aesDecrypt(loginDto.getPassword()));
        Subject currentUser = SecurityUtils.getSubject();
        try {
            // 开始对用户登录的参数进行校验
            verifyUser(loginDto.getUsername(), loginDto.getPassword());
            SecurityUtils.getSubject().logout();
            try {
                currentUser.login(token);
            } catch (Exception e) {
                log.error("错误信息{}", e);
            }

            userApiService.updateUserLoginInfo(loginDto.getUsername(), request);
            //从缓存里取用户信息
            UserVo userVo = JSON.parseObject(redisTemplate.opsForValue().get(CacheConstant.USER_CACHE +
                    CacheConstant.USER_CACHE_LOGIN_NAME_
                    + loginDto.getUsername()), UserVo.class);
            return new ResponseVo<>(ErrorEnum.SUCCESS, userVo);
        } catch (IncorrectCredentialsException e) {
            return new ResponseVo<>(ErrorEnum.USER_PASSWORD_ERROR);
        } catch (ExcessiveAttemptsException e) {
            return new ResponseVo<>(ErrorEnum.USER_EXCESSIVE_ATTEMPTS);
        } catch (LockedAccountException e) {
            return new ResponseVo<>(ErrorEnum.USER_LOCK_ERROR);
        } catch (DisabledAccountException e) {
            return new ResponseVo<>(ErrorEnum.USER_DISABLED_ERROR);
        } catch (ExpiredCredentialsException e) {
            return new ResponseVo<>(ErrorEnum.USER_EXPIRED_ERROR);
        } catch (UnknownAccountException e) {
            return new ResponseVo<>(ErrorEnum.USER_ACCOUNT_UNKNOWN);
        } catch (UnauthorizedException e) {
            return new ResponseVo<>(ErrorEnum.USER_NO_PERMISSION);
        } catch (AuthenticationException e) {
            return new ResponseVo<>(-1, e.getMessage());
        } catch (BusinessException e) {
            return new ResponseVo<>(-1, e.getMessage());
        } finally {
            SecurityUtils.getSubject().getSession().removeAttribute(CaptchaController.KEY_CAPTCHA);
        }
    }


    public void verifyUser(String account, String pwIdent) {

        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(pwIdent)) {
            throw new BusinessException("用户名/密码不能为空");
        }
        // 判断用户是否已超出登录限制
        log.info("---------------- 判断用户是否已超出登录限制 ----------------");
        this.verifyPasswordBefore(account);
        Boolean result = Boolean.FALSE;
        // 用户以用户名/密码的方式进行登录，校验密码是否匹配
        ResponseVo<UserVo> userVo = userApiService.getUserByLoginName(account);
        UserVo userVo1 = userVo.getData();
        if (userVo1 != null) {
            boolean passwordResult = AesUtils.validatePassword(AesUtils.aesDecrypt(pwIdent), userVo1.getPassword());
            if (passwordResult) {
                result = Boolean.TRUE;
            }
        } else {
            result = Boolean.FALSE;
        }
        // 对登录参数校验后，给出最后的结果处理
        log.info("---------------- 对登录参数校验后，给出最后的结果处理：【result:" + result + "】,result的结果如果为false,密码校验失败（用户名/密码登录）----------------");
        this.verifyPasswordAfter(result, account);
    }

    /**
     * 判断用户是否已超出登录限制
     * 主要还是判断用户账号是否超出限制
     *
     * @param account
     */
    private void verifyPasswordBefore(String account) {
        // 拼接redis缓存的key
        String loginTimesKey = MessageFormat.format(CacheConstant.LOGIN_FAIL_TIMES_KEY, account);
        log.info("---------------- 拼接redis缓存的keyLoginTimesKey:" + loginTimesKey + "----------------");

        // 分别查询账户以及ip已有的限制条数
        Long times = timeOut(loginTimesKey);
        if (times >= CacheConstant.LOGIN_FAIL_TIMES) {
            // 超出登录账号限制5次，锁住用户账号，限制登录
            Long ttl = redisTemplate.getExpire(loginTimesKey);
            log.info("---------------- 超出登录账号限制次数tt1:" + ttl + "----------------");
            throw new BusinessException(-1, "该用户登录失败超过" + CacheConstant.LOGIN_FAIL_TIMES + "次，请" + (ttl / 3600 + "小时" + (ttl - ttl / 3600 * 3600) / 60) + "分钟后再试");
        }
    }

    /**
     * 对登录参数校验后，给出最后的结果处理
     *
     * @param flag
     * @param account
     */
    private void verifyPasswordAfter(Boolean flag, String account) throws BusinessException {
        // 拼接redis缓存的key
        String loginTimesKey = MessageFormat.format(CacheConstant.LOGIN_FAIL_TIMES_KEY, account);
        // 校验最终结果：正确
        if (flag) {
            // 如果有正确的登录情况，则将缓存中记录的错误缓存删除掉
            if (redisTemplate.hasKey(loginTimesKey)) {
                redisTemplate.delete(loginTimesKey);
            }
            return;
        }
        // 校验最终结果：错误
        if (!redisTemplate.hasKey(loginTimesKey)) {
            redisTemplate.opsForValue().set(loginTimesKey, "1");
        } else {
            redisTemplate.opsForValue().increment(loginTimesKey, 1);
            Long times = timeOut(loginTimesKey);
            if (times >= CacheConstant.LOGIN_FAIL_TIMES) {
                // 开始锁定改账号1小时
                redisTemplate.expire(loginTimesKey, CacheConstant.NEXT_LOGIN_TIMES, TimeUnit.HOURS);
            }
        }
        throw new BusinessException("该用户已登录失败" + redisTemplate.opsForValue().get(loginTimesKey) + "次，还剩下"
                + (CacheConstant.LOGIN_FAIL_TIMES - Integer.parseInt(redisTemplate.opsForValue().get(loginTimesKey))) + "次机会");


    }

    /**
     * 用户登录次数
     *
     * @param loginTimesKey
     * @return
     */
    private Long timeOut(String loginTimesKey) {
        String timesStr = redisTemplate.opsForValue().get(loginTimesKey);
        long times = Long.parseLong(timesStr == null ? "0" : timesStr);
        log.info("---------------- 用户账户限制次数times:" + times + "----------------");
        return times;
    }

    /**
     * @return
     */
    @ApiOperation("用户登出")
    @RequestLogging
    @PostMapping("/logout")
    public ResponseVo<String> logoutPost() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return new ResponseVo<>(ErrorEnum.SUCCESS);
    }

    @ApiOperation("检查token")
    @GetMapping("/verifyToken")
    public ResponseVo<String> verifyToken(HttpServletRequest request) {
        String token = request.getHeader(SystemConstant.USER_HEADER_TOKEN);
        if (token.isEmpty()) {
            return new ResponseVo<>(ErrorEnum.TOKEN_INVALID);
        }
        UserVo userVo = UserUtils.getUser();
        if (!redisTemplate.hasKey(CacheConstant.USER_CACHE + CacheConstant.USER_CACHE_USER_TOKEN_ +
                userVo.getLoginName())) {
            return new ResponseVo<>(ErrorEnum.TOKEN_INVALID);
        }
        String userToken = redisTemplate.opsForValue()
                .get(CacheConstant.USER_CACHE + CacheConstant.USER_CACHE_USER_TOKEN_ +
                        userVo.getLoginName());
        if (!token.equals(userToken)) {
            return new ResponseVo<>(ErrorEnum.USER_TOKEN_ERROR);
        }
        return new ResponseVo<>(ErrorEnum.SUCCESS);
    }
}
