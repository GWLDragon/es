package com.hcf.nszh.provider.system.config;

import com.hcf.nszh.common.config.BaseSwaggerConfig;
import com.hcf.nszh.common.enums.SwaggerEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author maruko
 * @Date 2019/6/13 0013
 **/
@SpringBootConfiguration
@EnableSwagger2
public class SwaggerConfig {
    @Value("#{'prod'.equals('${spring.profiles.active}')}")
    private boolean disableSwagger;

    @Bean
    public Docket createRestApi() {
        return BaseSwaggerConfig.createRestApi(disableSwagger, SwaggerEnum.SYSTEM_PROVIDER_CONTROLLER_PATH, SwaggerEnum.SYSTEM_PROVIDER_CONTROLLER_NAME);
    }

}
