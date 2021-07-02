package com.hcf.nszh.common.base;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author maruko
 * @param <T>
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class  BaseEntity<T extends Model<T>> extends Model<T> implements Serializable   {
    private static final long serialVersionUID = 164506294424449689L;
    private String createUserId;
    private String createUserName;
    private String updateUserId;
    private String updateUserName;
    private Date createTime;
    private Date updateTime;
}
