package com.hcf.nszh.common.security.shiro.filter;

import com.hcf.nszh.common.security.shiro.config.ShiroProperties;
import com.hcf.nszh.common.security.shiro.utils.UserUtils;
import com.hcf.nszh.common.security.shiro.condition.FbsShiroCondition;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author huangxiong
 * @Date 2019/8/5
 **/
@Slf4j
@Conditional(FbsShiroCondition.class)
@Component
public class FbsAccessControlFilter extends AccessControlFilter {

    @Autowired
    private ShiroProperties shiroProperties;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        log.info("isAccessAllowed isAuthenticated:{}", subject.isAuthenticated());
        if (!subject.isAuthenticated()) {
            return false;
        }
        UserUtils.keepCurrentUserCache(shiroProperties.getExpireIn(), null);
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        log.info("onAccessDenied isAuthenticated:{}", subject.isAuthenticated());
        if (!subject.isAuthenticated()) {
            WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }
}
