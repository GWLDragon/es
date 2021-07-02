package com.hcf.nszh.provider.system.interceptor;

import com.hcf.nszh.common.interceptor.BaseFeignRequestInterceptor;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.http.HttpServletRequest;
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
        Map<String, String> headers = getHeaders(getHttpServletRequest());
        template.header("cookie", headers.get("cookie"));
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
