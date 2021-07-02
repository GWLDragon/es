package com.hcf.nszh.provider.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import com.hcf.nszh.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Author huangxiong
 * @Date 2019/7/1
 **/
@TableName("sys_role")
@Data
@EqualsAndHashCode(callSuper=false)
public class RoleEntity extends BaseEntity<RoleEntity> {
    private static final long serialVersionUID = 4241055222254177099L;
    @TableId(value = "role_id", type = IdType.ASSIGN_ID)
    private String roleId;
    private OfficeEntity office;
    private String name;
    private String enname;
    private String roleType;
    private String dataScope;
    private String remarks;

    private String oldName;
    private String oldEnname;
    private String isSys;
    private String useable;
    private String createBy;
    private Date createDate;
    private String updateBy;
    private Date updateDate;
    private String roleCode;
    private String delFlag;

    /**
     * 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
     */
    public static final String DATA_SCOPE_ALL = "1";
    public static final String DATA_SCOPE_COMPANY_AND_CHILD = "2";
    public static final String DATA_SCOPE_COMPANY = "3";
    public static final String DATA_SCOPE_OFFICE_AND_CHILD = "4";
    public static final String DATA_SCOPE_OFFICE = "5";
    public static final String DATA_SCOPE_SELF = "8";
    public static final String DATA_SCOPE_CUSTOM = "9";
}
