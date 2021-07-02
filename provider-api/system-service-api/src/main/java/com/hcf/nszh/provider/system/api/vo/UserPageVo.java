package com.hcf.nszh.provider.system.api.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author huangxiong
 * @Date 2019/7/11
 **/
@Data
public class UserPageVo implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String officeId;
    private String loginName;
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
    private List<String> roleNames;

    private List<String> roleIds;

    private String officeName;
}
