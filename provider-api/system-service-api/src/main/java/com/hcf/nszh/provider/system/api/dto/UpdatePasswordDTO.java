package com.hcf.nszh.provider.system.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author huangxiong
 * @Date 2019/7/11
 **/
@Data
public class UpdatePasswordDTO implements Serializable {

    @ApiModelProperty("用户登录名")
    @NotNull(message = "用户登录名不能为空")
    private String loginName;

    @ApiModelProperty("新密码")
    @NotNull(message = "新密码不能为空")
    private String newPassword;

    @ApiModelProperty("原始密码")
    @NotNull(message = "原始密码不能为空")
    private String oldPassword;

}
