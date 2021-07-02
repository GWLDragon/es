package com.hcf.nszh.provider.system.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Furant
 * 2019/7/20 10:46
 */
@Data
public class SearchByConditionDTO implements Serializable {

    @ApiModelProperty("当前页")
    private Integer pageNum=1;

    @ApiModelProperty("页数大小")
    private Integer pageSize=10;
    /**
     * 菜单名称
     */
    @ApiModelProperty("搜索名称")
    private String name;
}
