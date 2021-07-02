package com.hcf.nszh.consumer.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author hx
 * @Date 2019/6/26
 **/
@ComponentScan({"com.hcf.nszh.*"})
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@RefreshScope
@EnableFeignClients(basePackages = {"com.hcf.nszh"})
public class SystemConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemConsumerApplication.class, args);
    }

}
