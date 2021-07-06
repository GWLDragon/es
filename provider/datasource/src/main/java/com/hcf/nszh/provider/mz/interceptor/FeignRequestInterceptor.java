package com.hcf.nszh.provider.mz.interceptor;

import com.hcf.nszh.common.interceptor.BaseFeignRequestInterceptor;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author maruko
 * @Date 2019/7/31
 **/
@Component
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        if (null != httpServletRequest) {
            Map<String, String> headers = getHeaders(httpServletRequest);
            template.header("cookie", headers.get("cookie"));
        }
    }

    private HttpServletRequest getHttpServletRequest() {
        return BaseFeignRequestInterceptor.getHttpServletRequest();
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        return BaseFeignRequestInterceptor.getHeaders(request);
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

}
