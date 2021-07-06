package com.hcf.nszh.provider.mz.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 〈一句话功能简述〉<br>
 * 〈jdbc连接实体类〉
 *
 * @author maruko
 * @date 2021/7/6 16:12
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "JdbcEntity", description = "数据库连接")
public class JdbcEntity {

    @ApiModelProperty("序号")
    private Long id;

    @ApiModelProperty("连接驱动")
    private String driverClassType;

    @ApiModelProperty("连接路径")
    private String url;

    @ApiModelProperty("登录用户")
    private String userName;

    @ApiModelProperty("登录密码")
    private String password;

    public JdbcEntity(String driverClassType, String url, String userName, String password) {
        this.driverClassType = driverClassType;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }
}
