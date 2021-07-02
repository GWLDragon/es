package com.hcf.nszh.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author maruko
 * 全局解决不支持跨域访问问题
 * <p>
 * 方式二：添加   @CrossOrigin // 注解方式
 */
@Configuration
public class CorsConfig {

    /**
     * 默认是不限制访问
     */
    @Value("${web.cors.allowed:*}")
    private String[] allowedOrigins;


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(true)
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods("GET", "POST")
                ;
            }
        };
    }
}
