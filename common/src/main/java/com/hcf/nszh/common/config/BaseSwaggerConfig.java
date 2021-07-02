package com.hcf.nszh.common.config;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Calendar;

/**
 * 〈一句话功能简述〉<br>
 * 〈swagger配置〉
 *
 * @author maruko
 * @date 2021/7/2 16:54
 * @since 1.0.0
 */

public class BaseSwaggerConfig {

    /**
     * @param disableSwagger 是否显示swagger
     * @param path           controller路径
     * @param name           api名称
     * @return
     */
    public static Docket createRestApi(Boolean disableSwagger, String path, String name) {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(!disableSwagger)
                .apiInfo(apiInfo(name))
                .select()
                .apis(RequestHandlerSelectors.
                        basePackage(path))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * swagger信息
     *
     * @param name api名称
     * @return
     */
    private static ApiInfo apiInfo(String name) {
        return new ApiInfoBuilder()
                .title(name + "接口API")
                .description("©" + Calendar.getInstance().get(Calendar.YEAR) + " Copyright. Powered By Maruko")
                .build();
    }
}
