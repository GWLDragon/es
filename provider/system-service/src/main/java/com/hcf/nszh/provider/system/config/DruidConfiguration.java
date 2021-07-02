package com.hcf.nszh.provider.system.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author huangxiong
 * @Date 2019/6/22
 **/
@Configuration
@EnableConfigurationProperties(value = DruidDataSourceProperties.class)
public class DruidConfiguration {

    @Autowired
    private DruidDataSourceProperties druidDataSourceProperties;

    @Bean     //声明其为Bean实例
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
    public DruidDataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(druidDataSourceProperties.getJdbcUrl());
        datasource.setUsername(druidDataSourceProperties.getUsername());
        datasource.setPassword(druidDataSourceProperties.getPassword());
        datasource.setDriverClassName(druidDataSourceProperties.getDriverClassName());
        //configuration
        datasource.setInitialSize(druidDataSourceProperties.getInitialSize());
        datasource.setMinIdle(druidDataSourceProperties.getMinIdle());
        datasource.setMaxActive(druidDataSourceProperties.getMaxActive());
        datasource.setMaxWait(druidDataSourceProperties.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(druidDataSourceProperties.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(druidDataSourceProperties.getMinEvictableIdleTimeMillis());
        datasource.setPoolPreparedStatements(druidDataSourceProperties.isPoolPreparedStatements());
        datasource.setMaxPoolPreparedStatementPerConnectionSize(
                druidDataSourceProperties.getMaxPoolPreparedStatementPerConnectionSize());
        return datasource;
    }

    /**
     * 配置druid管理后台的servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewSerlvet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        Map<String,Object> initParameters = new HashMap<>();
        initParameters.put("loginUsername",druidDataSourceProperties.getDruidUserName());
        initParameters.put("loginPassword",druidDataSourceProperties.getDruidPassword());
        bean.setInitParameters(initParameters);
        return bean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));

        Map<String,Object> initParams = new HashMap<>();
        initParams.put("exclusions","*.js,*.css,/druid/*");
        filterRegistrationBean.setInitParameters(initParams);
        return  filterRegistrationBean;
    }
}
