package com.hcf.nszh.provider.mz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import java.util.TimeZone;


/**
 * @Author huangxiong
 * @Date 2019/7/1
 **/
@ComponentScan(basePackages= {"com.hcf.nszh.*"})
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients(basePackages = {"com.hcf.nszh"})
@MapperScan("com.hcf.nszh.provider.mz.mapper")
@EnableTransactionManagement
public class StatisticsProviderApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(StatisticsProviderApplication.class, args);
    }
}
