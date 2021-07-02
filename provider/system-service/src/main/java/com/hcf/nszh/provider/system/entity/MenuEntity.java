package com.hcf.nszh.provider.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hcf.nszh.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author hx
 * @Date 2019/7/1
 **/
@TableName("sys_menu")
@Data
@EqualsAndHashCode(callSuper=false)
public class MenuEntity extends BaseEntity<MenuEntity> {
    private static final long serialVersionUID = 2412249536753980099L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    @TableField(exist = false)
    private MenuEntity parent;
    private String parentId;
    private String parentIds;
    private String name;
    private String href;
    private String target;
    private String icon;
    private BigDecimal sort;
    private String isShow;
    private String permission;
    private String createBy;
    private Date createDate;
    private String updateBy;
    private Date updateDate;
    private String remarks;
    private String delFlag;
    @TableField(exist = false)
    private String dbName;
    @TableField(exist = false)
    private Long userId;
}
