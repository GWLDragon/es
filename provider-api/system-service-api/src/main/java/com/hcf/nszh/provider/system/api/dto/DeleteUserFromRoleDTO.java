package com.hcf.nszh.provider.system.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Furant
 * 2019/7/27 15:33
 */
@Data
public class DeleteUserFromRoleDTO implements Serializable {

    @ApiModelProperty("要移除用户的id")
    private Long userId;

    @ApiModelProperty("角色的id")
    private String roleId;

}
