package com.hcf.nszh.provider.system.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Furant
 * 2019/7/5 11:53
 */
@Data
public class OperatorMenuDto implements Serializable {

    @ApiModelProperty(value = "菜单Id")
    private String id;

    @ApiModelProperty(value = "菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    @Size(min = 1,max = 30,message = "菜单名称为1~500个字符")
    private String name;

    @ApiModelProperty(value = "链接")
    @NotBlank(message = "链接不能为空")
    @Size(min = 1,max = 500,message = "链接长度为1~500个字符")
    private String href;

    @ApiModelProperty(value = "目标")
    private String target;

    @Size(min = 0,max = 30,message = "图标不能超出50个字符")
    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "排序")
    private BigDecimal sort;

    @Size(min = 0,max = 500,message = "权限标识不能超出500个字符")
    @ApiModelProperty(value = "权限标识")
    private String permission;

    @Size(min = 0,max = 50,message = "备注不能超出50个字符")
    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "父类菜单")
    private String parentId;

    @ApiModelProperty(value = "所有父级编号")
    private String parentIds;

    @ApiModelProperty(value = "是否可见")
    private String isShow;

    @ApiModelProperty(value = "更新者")
    private String updateBy;
}

