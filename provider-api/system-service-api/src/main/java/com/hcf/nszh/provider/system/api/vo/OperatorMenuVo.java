package com.hcf.nszh.provider.system.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Furant
 * 2019/7/8 10:54
 */
@Data
public class OperatorMenuVo implements Serializable {

    private String id;
    @ApiModelProperty("父级菜单")
    private OperatorMenuVo parent;

    private String parentId;
    @ApiModelProperty("所有父级编号")
    private String parentIds;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("链接")
    private String href;
    @ApiModelProperty("目标（ mainFrame、_blank、_self、_parent、_top）")
    private String target;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty("排序")
    private BigDecimal sort;
    @ApiModelProperty("是否在菜单中显示（1：显示；0：不显示）")
    private String isShow;
    @ApiModelProperty("权限标识")
    private String permission;
    @ApiModelProperty("创建者")
    private String createBy;
    @ApiModelProperty("创建时间")
    private Date createDate;
    @ApiModelProperty("更新者")
    private String updateBy;
    @ApiModelProperty("更新时间")
    private Date updateDate;
    @ApiModelProperty("备注信息")
    private String remarks;
    @ApiModelProperty("删除标记")
    private String delFlag;
    @ApiModelProperty("数据库类型")
    private String dbName;
}
