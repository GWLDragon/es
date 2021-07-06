package com.hcf.nszh.provider.mz.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "SqlEntity", description = "sql实体")
public class SqlEntity {
    @ApiModelProperty("序号")
    private Long id;

    @ApiModelProperty("数据库编号")
    private Long jdbcId;

    @ApiModelProperty("执行sql")
    private String sql;
}
