package com.hcf.nszh.provider.system.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Furant
 * 2019/7/28 14:53
 */
@Data
public class ListAssignedUserDTO implements Serializable {

    @ApiModelProperty("当前页")
    private Integer pageNum;

    @ApiModelProperty("页面大小")
    private Integer pageSize;

    @ApiModelProperty("角色Id")
    private String roleId;

}
