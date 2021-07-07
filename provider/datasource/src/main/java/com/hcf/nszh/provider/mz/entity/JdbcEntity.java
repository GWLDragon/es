package com.hcf.nszh.provider.mz.entity;

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

public class JdbcEntity {

    /**
     * 序号
     */
    private Long id;

    /**
     * 连接驱动
     */
    private String driverClassType;

    /**
     * 连接路径
     */
    private String url;

    /**
     * 登录用户
     */
    private String userName;

    /**
     * 登录密码
     */
    private String password;

    public JdbcEntity(String driverClassType, String url, String userName, String password) {
        this.driverClassType = driverClassType;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }
}
