package com.hcf.nszh.provider.system.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Furant
 * 2019/7/3 20:38
 */
@Data
public class OperatorRoleDto implements Serializable {

    @ApiModelProperty("角色Id")
    private String roleId;

    @ApiModelProperty("角色名称")
    @NotBlank(message = "角色名称不能为空")
    @Size(min = 1,max = 20,message = "角色名称长度为1~20个字符")
    private String name;

    @ApiModelProperty("英文名称/编码")
    private String enname;

    @ApiModelProperty("权限类型")
    private String roleType;

    @ApiModelProperty("数据范围")
    private String dataScope;

    @ApiModelProperty("是否是系统数据")
    private String isSys;

    @ApiModelProperty("是否是可用")
    private String useable;

    @ApiModelProperty("操作人id")
    private String updateBy;

    @ApiModelProperty("备注")
    private String remarks;


    @ApiModelProperty("菜单的id")
    private List<String> menuIds;



}