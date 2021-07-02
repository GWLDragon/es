package com.hcf.nszh.provider.system.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author huangxiong
 * @Date 2019/6/29
 **/
@Data
public class MenuVo {

    @ApiModelProperty("菜单id")
    private String id;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("父级菜单id")
    private String parentId;

    @ApiModelProperty("链接")
    private String href;

    @ApiModelProperty("排序")
    private BigDecimal sort;

    @ApiModelProperty("是否显示 1：显示；0：不显示")
    private String isShow;

    @ApiModelProperty("权限标识")
    private String permission;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("子节点")
    private List<MenuVo> children;

    private String remarks;

}
