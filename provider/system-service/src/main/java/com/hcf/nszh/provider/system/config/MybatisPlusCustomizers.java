package com.hcf.nszh.provider.system.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import org.apache.ibatis.type.JdbcType;

/**
 * @author lc
 */
public class MybatisPlusCustomizers implements ConfigurationCustomizer {

    @Override
    public void customize(org.apache.ibatis.session.Configuration configuration) {
        configuration.setJdbcTypeForNull(JdbcType.NULL);
    }
}