package com.hcf.nszh.common.security.foresee.cas;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author huangxiong
 * @Date 2019/7/18
 **/
@Data
@ConfigurationProperties(prefix = "foresee.cas")
public class SSOProperties {
    private String loginUrl;
    private String validateUrl;
    private String loginClassName;
    private String sessionName;
    private String filterName;
    private String serverName;
    private String urlPatterns;
}
