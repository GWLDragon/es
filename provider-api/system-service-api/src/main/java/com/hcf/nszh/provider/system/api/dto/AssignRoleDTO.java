package com.hcf.nszh.provider.system.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Furant
 * 2019/7/23 11:40
 */
@Data
public class AssignRoleDTO implements Serializable {

    private List<Long> userIds;

    private String roleId;
}
