package com.hcf.nszh.provider.mz.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据源配置表
 * </p>
 *
 * @author LC
 * @since 2020-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_DATASOURCE")
@ApiModel(value="Datasource对象", description="数据源配置表")
public class Datasource extends Model<Datasource> {

private static final long serialVersionUID=1L;

    @TableId(value = "SID", type = IdType.AUTO)
    private Long sid;

    @ApiModelProperty(value = "驱动")
    @TableField("DRIVER")
    private String driver;

    @ApiModelProperty(value = "数据库链接地址")
    @TableField("URL")
    private String url;

    @ApiModelProperty(value = "用户名")
    @TableField("USERNAME")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("PASSWORD")
    private String password;

    @ApiModelProperty(value = "删除标记（0：正常；1：删除)")
    @TableField("DEL_FLAG")
    private String delFlag;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
