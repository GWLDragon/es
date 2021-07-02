package com.hcf.nszh.provider.system.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author huangxiong
 * @Date 2019/7/25
 **/
@ApiModel(value = "删除用户传入信息")
@Data
public class DeleteUserDto implements Serializable {
    @ApiModelProperty("用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long userId;
}
