package com.hcf.nszh.provider.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hcf.nszh.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author huangxiong
 * @Date 2019/7/1
 **/
@Data
@TableName("sys_area")
@EqualsAndHashCode(callSuper=false)
public class AreaEntity extends BaseEntity<AreaEntity> {

    private String id;
    private String code;

    private String name;


    private String type;
}
