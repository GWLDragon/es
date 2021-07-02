package com.hcf.nszh.consumer.system.config;

import com.hcf.nszh.common.config.BaseSwaggerConfig;
import com.hcf.nszh.common.enums.SwaggerEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author hx
 * @Date 2019/6/13 0013
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("#{'prod'.equals('${spring.profiles.active}')}")
    private boolean disableSwagger;

    @Bean
    public Docket createRestApi() {
        return BaseSwaggerConfig.createRestApi(disableSwagger, SwaggerEnum.SYSTEM_CONSUME_CONTROLLER_PATH, SwaggerEnum.SYSTEM_CONSUME_CONTROLLER_NAME);
    }
}
