package com.hcf.nszh.provider.system.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Furant
 * 2019/7/22 10:41
 */
@Data
public class OfficeDetailVO implements Serializable {

    private String id;

    @ApiModelProperty("归属区域")
    private AreaDetailVO area;

    private String areaId;

    private String parentId;

    private String parentIds;


    @ApiModelProperty("机构编码")
    private String code;


    @ApiModelProperty("机构名称")
    private String name;

    @ApiModelProperty("排序")
    private BigDecimal sort;

    @ApiModelProperty(" 机构类型（1：公司；2：部门；3：小组）")
    private String type;

    @ApiModelProperty("机构等级（1：一级；2：二级；3：三级；4：四级）")
    private String grade;

    @ApiModelProperty("联系地址")
    private String address;

    @ApiModelProperty("邮政编码")
    private String zipCode;

    @ApiModelProperty("负责人")
    private String master;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("传真")
    private String fax;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("是否可用")
    private String useable;

    @ApiModelProperty("主负责人")
    private String primaryPerson;

    @ApiModelProperty("副负责人")
    private String deputyPerson;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String remarks;

    private String delFlag;

    private OfficeDetailVO parent;
    private List<String> childDeptList;
}
