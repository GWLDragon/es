package com.hcf.nszh.provider.mz.config;

import com.google.common.base.Strings;
import com.hcf.nszh.provider.mz.entity.JdbcEntity;
import com.hcf.nszh.provider.mz.enums.JdbcEnum;
import freemarker.template.utility.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 〈一句话功能简述〉<br>
 * 〈数据库连接配置〉
 *
 * @author maruko
 * @date 2021/7/6 16:30
 * @since 1.0.0
 */
@Slf4j
@Component
public class JdbcConfig {

    /**
     * @param jdbcEntity 数据库配置信息
     * @return
     */
    public static Connection getConnection(JdbcEntity jdbcEntity) {
        try {
            //判断是否包含对应驱动
            String driverClass = JdbcEnum.getDriverClassByType(jdbcEntity.getDriverClassType());
            if (Strings.isNullOrEmpty(driverClass)) {
                log.error("暂不支持该数据库驱动");
                throw new Exception("暂不支持该数据库驱动，请联系管理员");
            }
            ClassUtil.forName(driverClass);
            Properties info = new Properties() {{
                setProperty("user", jdbcEntity.getUserName());
                setProperty("password", jdbcEntity.getPassword());
            }};
            return DriverManager.getConnection(jdbcEntity.getUrl(), info);
        } catch (Exception e) {
            log.error("获取数据源连接失败", e);
            return null;
        }
    }
}
