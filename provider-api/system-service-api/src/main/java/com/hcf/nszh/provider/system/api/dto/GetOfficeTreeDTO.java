package com.hcf.nszh.provider.system.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Furant
 * 2019/7/23 9:54
 */
@Data
public class GetOfficeTreeDTO implements Serializable {

    @ApiModelProperty("排除id")
    private String extId;

    @ApiModelProperty("机构类型")
    private String type;

    @ApiModelProperty("机构等级")
    private Long grade;

    @ApiModelProperty("是否全部")
    private Boolean isAll;
}
