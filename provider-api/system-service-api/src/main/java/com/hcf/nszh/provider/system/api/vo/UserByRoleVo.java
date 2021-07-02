package com.hcf.nszh.provider.system.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author ygr
 * @Date 2019/8/29
 **/
@ApiModel(value = "用户返回信息")
@Data
public class UserByRoleVo implements Serializable {
    private Long userId;
    @ApiModelProperty("机构")
    private String officeId;
    @ApiModelProperty("机构名称")
    private String officeName;
    @ApiModelProperty("登录名")
    private String loginName;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("电话")
    private String phone;
    private String loginFlag;
    @ApiModelProperty("头像")
    private String photo;
    @SuppressWarnings("unused")
    private boolean admin;

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @ApiModelProperty("删除标记（0：正常；1：删除；")
    protected String delFlag;
}
