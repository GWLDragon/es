package com.hcf.nszh.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author maruko
 * @date 2021/7/2 17:34
 * @since 1.0.0
 */
@Slf4j
public class BaseFeignRequestInterceptor {


    public static HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            log.error("HttpServletRequest :{}", e);
            return null;
        }
    }

    public static Map<String, String> getHeaders(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement().toLowerCase();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
