package com.hcf.nszh.provider.system.entity;

import com.hcf.nszh.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author  by Furant
 * 2019/7/24 11:29
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RoleMenuEntity extends BaseEntity<RoleMenuEntity>{


    private String roleId;

    private String menuId;
}
