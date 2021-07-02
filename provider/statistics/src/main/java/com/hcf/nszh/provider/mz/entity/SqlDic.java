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
 * 
 * </p>
 *
 * @author LC
 * @since 2020-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_SQL_DIC")
@ApiModel(value="SqlDic对象")
public class SqlDic extends Model<SqlDic> {

private static final long serialVersionUID=1L;

    @TableId(value = "SID", type = IdType.AUTO)
    private Long sid;

    @ApiModelProperty(value = "唯一，可用于查询initial_sql")
    @TableField("SKEY")
    private String skey;

    @ApiModelProperty(value = "初始的sql语句")
    @TableField("INITIAL_SQL")
    private String initialSql;

    @ApiModelProperty(value = "删除标记（0：正常；1：删除)")
    @TableField("DEL_FLAG")
    private String delFlag;

    @ApiModelProperty(value = "数据源表id")
    @TableField("DATASOURCE_ID")
    private Long datasourceId;


    @Override
    protected Serializable pkVal() {
        return this.sid;
    }

}
