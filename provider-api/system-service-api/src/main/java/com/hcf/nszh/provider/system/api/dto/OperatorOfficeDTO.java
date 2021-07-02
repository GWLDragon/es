package com.hcf.nszh.provider.system.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Furant
 * 2019/7/22 10:48
 */
@Data
public class OperatorOfficeDTO implements Serializable {

    private String id;

    private String areaId;

    private String parentId;

    private String parentIds;

    @Size(min = 1, max = 30, message = "角色名称长度为1~30个字符")
    @NotBlank(message = "机构编码不能为空")
    private String code;

    @Size(min = 1, max = 30, message = "角色名称长度为1~30个字符")
    @NotBlank(message = "机构名称不能为空")
    private String name;


    private BigDecimal sort;


    private String type;


    private String grade;

    @Size(max = 50, message = "联系地址长度不能超出50个字")
    private String address;

    @Size(max = 50, message = "邮政编码长度不能超出50个字")
    private String zipCode;


    private String master;

    @Size(max = 50, message = "电话长度不能超出50个字")
    private String phone;

    @Size(max = 50, message = "传真长度不能超出50个字")
    private String fax;

    @Size(max = 50, message = "邮箱长度不能超出50个字")
    private String email;


    private String useable;


    private String primaryPerson;


    private String deputyPerson;


    private String updateBy;

    @Size(max = 50, message = "备注长度不能超出50个字")
    private String remarks;

    private OperatorOfficeDTO parent;

}
