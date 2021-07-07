package com.hcf.nszh.provider.mz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 〈一句话功能简述〉<br>
 * 〈sql实体类〉
 *
 * @author maruko
 * @date 2021/7/6 18:02
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SqlEntity {
    /**
     * 序号
     */
    private Long id;

    /**
     * 数据库编号
     */
    private Long jdbcId;

    /**
     * 执行sql
     */
    private String sql;
}
