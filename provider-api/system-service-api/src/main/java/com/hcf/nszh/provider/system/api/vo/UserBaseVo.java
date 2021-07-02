package com.hcf.nszh.provider.system.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author ygr
 * @Date 2019/6/29
 **/
@ApiModel(value = "用户返回信息")
@Data
public class UserBaseVo implements Serializable {
    private Long userId;

    @ApiModelProperty("登录名")
    private String loginName;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("电话")
    private String phone;


}
