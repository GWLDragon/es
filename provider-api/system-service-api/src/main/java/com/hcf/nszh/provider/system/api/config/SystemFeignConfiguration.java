package com.hcf.nszh.provider.system.api.config;

import feign.Feign;
import feign.Logger;
import feign.hystrix.HystrixFeign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @Author huangxiong
 * @Date 2019/6/6 0006
 **/
@Configuration
public class SystemFeignConfiguration {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        //默认支持熔断器
        return HystrixFeign.builder();
    }
}
