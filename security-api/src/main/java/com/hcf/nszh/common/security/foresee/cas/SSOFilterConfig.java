package com.hcf.nszh.common.security.foresee.cas;

import com.foresee.its.tp.cas.client.filter.CASFilter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author huangxiong
 * @Date 2019/7/3
 **/
@Configuration
@ConditionalOnProperty(name = "foresee.cas.enabled", matchIfMissing = false)
@EnableConfigurationProperties(value = SSOProperties.class)
public class SSOFilterConfig {
    @Autowired
    private SSOProperties ssoProperties;

    @Bean
    public FilterRegistrationBean registerForeseeFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CASFilter());
        //设置要认证的url
        String[] urlPatterns = StringUtils.
                splitByWholeSeparatorPreserveAllTokens(ssoProperties.getUrlPatterns(), ",");
        for (String url : urlPatterns) {
            registration.addUrlPatterns(url);
        }

        registration.addInitParameter("com.foresee.its.tp.cas.client.filter.loginUrl",
                ssoProperties.getLoginUrl());
        registration.addInitParameter("com.foresee.its.tp.cas.client.filter.validateUrl",
                ssoProperties.getValidateUrl());
       registration.addInitParameter("com.foresee.its.tp.cas.client.filter.loginClassName",
                ssoProperties.getLoginClassName());
        registration.addInitParameter("com.foresee.its.tp.cas.client.filter.sessionName",
                ssoProperties.getSessionName());
        registration.addInitParameter("com.foresee.its.tp.cas.client.filter.serverName",
                ssoProperties.getServerName());
        registration.setName(ssoProperties.getFilterName());
        registration.setOrder(1);
        return registration;

    }
}
