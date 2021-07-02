package com.hcf.nszh.provider.system.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class AreaVo implements Serializable {


    @ApiModelProperty("区域编码")
    private String code;

    @ApiModelProperty("区域名称")
    private String name;

    @ApiModelProperty("类型")
    private String type;


}
