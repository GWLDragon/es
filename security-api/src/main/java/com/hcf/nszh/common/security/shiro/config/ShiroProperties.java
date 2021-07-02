package com.hcf.nszh.common.security.shiro.config;

import com.hcf.nszh.common.security.shiro.condition.FbsShiroCondition;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * @Author huangxiong
 * @Date 2019/6/26
 **/
@Component
@Conditional(FbsShiroCondition.class)
@ConfigurationProperties(prefix = "fbs.shiro")
@Data
public class ShiroProperties {
    //登录 url
    private String loginUrl;
    private int expireIn = 1800;
    private long sessionTimeout = 1800000;
    private long sessionTimeoutClean = 120000;
    private long cookieTimeout;
    //免认证的路径配置，如静态资源，druid监控页面，注册页面，验证码请求等
    private String anonUrl;
    private String unauthorizedUrl;
    //需要控制的地址
    private String authcUrl;
    //登出 url
    private String logoutUrl;
    private String successUrl;
    private String sessionIdName;
    private String multiAccountLogin;
    private Boolean noPassword = false;
}
