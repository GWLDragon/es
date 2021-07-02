package com.hcf.nszh.consumer.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author maruko
 * @Date 2019/6/26
 **/
@ApiModel(value = "用户登录传入信息")
@Data
public class LoginDto implements Serializable {

    private static final long serialVersionUID = 748549111877662113L;
    @ApiModelProperty("用户账号")
    @NotNull(message = "用户账号不能为空")
    private String username;
    @ApiModelProperty("用户密码")
    @NotNull(message = "密码不能为空")
    private String password;
    @ApiModelProperty("验证码")
    private String captcha;
}
