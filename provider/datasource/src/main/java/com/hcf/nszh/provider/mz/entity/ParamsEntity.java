package com.hcf.nszh.provider.mz.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 〈一句话功能简述〉<br>
 * 〈参数详情〉
 *
 * @author maruko
 * @date 2021/7/6 17:07
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "SqlEntity", description = "参数实体")
public class ParamsEntity {

    @ApiModelProperty("序号")
    private Long id;

    @ApiModelProperty("sql序号")
    private Long sqlId;

    @ApiModelProperty("参数名称")
    private String name;

    @ApiModelProperty("参数值")
    private Object value;

    @ApiModelProperty("参数类型")
    private String type;

}
