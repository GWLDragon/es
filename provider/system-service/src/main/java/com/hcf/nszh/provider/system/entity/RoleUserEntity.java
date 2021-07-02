package com.hcf.nszh.provider.system.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hcf.nszh.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author  by Furant
 * 2019/7/23 11:45
 */
@Data
@TableName("sys_user_role")
@EqualsAndHashCode(callSuper=false)
public class RoleUserEntity extends BaseEntity<RoleUserEntity> {

    @TableId
    private Long userId;

    @TableId
    private String roleId;
}
