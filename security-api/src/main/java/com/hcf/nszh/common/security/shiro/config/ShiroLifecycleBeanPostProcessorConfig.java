package com.hcf.nszh.common.security.shiro.config;

import com.hcf.nszh.common.security.shiro.condition.FbsShiroCondition;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @Author huangxiong
 * @Date 2019/6/26
 **/
@Configuration
@Conditional(FbsShiroCondition.class)
public class ShiroLifecycleBeanPostProcessorConfig {
    /**
     * Shiro生命周期处理器
     *
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
