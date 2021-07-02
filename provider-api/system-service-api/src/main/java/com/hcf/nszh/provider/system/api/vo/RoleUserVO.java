package com.hcf.nszh.provider.system.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Furant
 * 2019/7/25 11:38
 */
@Data
public class RoleUserVO implements Serializable {

    private Long userId;

    private String roleId;
}
