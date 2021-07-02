package com.hcf.nszh.provider.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author  smlz on 2019/3/22.
 */
@Data
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DruidDataSourceProperties {

    private String username;

    private String password;

    private String jdbcUrl;

    private String driverClassName;

    private Integer initialSize;

    private Integer maxActive;

    private Integer minIdle;

    private long maxWait;

    private boolean poolPreparedStatements;

    private int timeBetweenEvictionRunsMillis;

    private int minEvictableIdleTimeMillis;

    private int maxPoolPreparedStatementPerConnectionSize;

    private String druidUserName;

    private String druidPassword;

}
