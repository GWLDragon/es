package com.hcf.nszh.provider.system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hcf.nszh.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author hx
 * @Date 2019/7/1
 **/
@TableName("sys_office")
@Data
@EqualsAndHashCode(callSuper=false)
public class OfficeEntity extends BaseEntity<OfficeEntity> {
    private static final long serialVersionUID = -4745754517834884121L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Transient
    @TableField(exist = false)
    private AreaEntity area;

    private String areaId;

    private String parentId;

    private String parentIds;


    private String code;


    private String name;


    private BigDecimal sort;


    private String type;


    private String grade;


    private String address;


    private String zipCode;


    private String master;

    private String phone;


    private String fax;


    private String email;


    private String useable;

    @Transient
    private String primaryPerson;

    @Transient
    private String deputyPerson;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String remarks;

    private String delFlag;


    @TableField(exist = false)
    @Transient
    private OfficeEntity parent;
    @TableField(exist = false)
    @Transient
    private List<String> childDeptList;

    /**
     * 自定义SQL（SQL标识，SQL内容）
     */
    protected Map<String, String> sqlMap;
}
