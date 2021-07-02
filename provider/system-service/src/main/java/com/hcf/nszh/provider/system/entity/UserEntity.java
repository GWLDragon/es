package com.hcf.nszh.provider.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.google.common.collect.Lists;
import com.hcf.nszh.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

/**
 * @Author huangxiong
 * @Date 2019/7/1
 **/
@TableName("sys_user")
@Data
@KeySequence(value = "SEQ_USER_ID")
@EqualsAndHashCode(callSuper=false)
public class UserEntity extends BaseEntity<UserEntity> {
    private static final long serialVersionUID = 6092441468539715889L;
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)

    private Long userId;
    private OfficeEntity company;
    private String companyId;
    private OfficeEntity office;
    private AreaEntity areaEntity;
    private String officeId;
    private String loginName;
    private String password;
    @TableField(jdbcType = JdbcType.VARCHAR)
    private String no;
    private String name;
    private String email;
    private String phone;
    private String mobile;
    private String userType;
    private String loginIp;
    private Date loginDate;
    private String loginFlag;
    private String photo;
    @SuppressWarnings("unused")
    private boolean admin;
    private String oldLoginName;
    private String newPassword;
    private String oldLoginIp;
    private Date oldLoginDate;

    private RoleEntity role;

    private String delFlag;
    private String remarks;
    private List<RoleEntity> roleList = Lists.newArrayList();

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    private String skin;
    private String sysSkin;

}
