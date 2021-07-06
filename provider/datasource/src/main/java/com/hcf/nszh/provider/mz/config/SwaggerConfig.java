package com.hcf.nszh.provider.mz.config;

import com.hcf.nszh.common.config.BaseSwaggerConfig;
import com.hcf.nszh.common.enums.SwaggerEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author hx
 * @Date 2019/6/13 0013
 **/
@SpringBootConfiguration
@EnableSwagger2
public class SwaggerConfig {
    @Value("#{'prod'.equals('${spring.profiles.active}')}")
    private boolean disableSwagger;

    @Bean
    public Docket createRestApi() {
        return BaseSwaggerConfig.createRestApi(disableSwagger, SwaggerEnum.STATISTICS_PROVIDER_CONTROLLER_PATH, SwaggerEnum.STATISTICS_PROVIDER_CONTROLLER_NAME);
    }
}
