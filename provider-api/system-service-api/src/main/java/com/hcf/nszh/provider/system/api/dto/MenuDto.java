package com.hcf.nszh.provider.system.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Furant
 * 2019/7/4 18:09
 */

@Data
public class MenuDto implements Serializable {
    @ApiModelProperty(value = "菜单Id",example = "00703d24645b4d0995d0fc1f99b5577e")
    private String menuId;
}
