package com.hcf.nszh.provider.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author huangxiong
 * @Date 2019/7/1
 **/
@ComponentScan({"com.hcf.nszh.*"})
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@RefreshScope
@EnableFeignClients(basePackages = {"com.hcf.nszh"})
@MapperScan("com.hcf.nszh.provider.system.mapper")
public class SystemProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemProviderApplication.class, args);
    }

}
