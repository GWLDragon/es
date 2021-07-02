package com.hcf.nszh.provider.system.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Furant
 * 2019/7/27 17:16
 */
@Data
public class AssignedUserVO implements Serializable {

    private Long userId;

    private String roleId;

    private String loginName;

    private String name;

    private String phone;

    private String email;

}
