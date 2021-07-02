package com.hcf.nszh.provider.system.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author huangxiong
 * @Date 2019/6/29
 **/
@Data
public class OfficeVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String parentIds;
    @ApiModelProperty("机构编码")
    private String code;
    @ApiModelProperty("机构名称")
    private String name;

    @ApiModelProperty("区域id")
    private String areaId;

    private String parentId;
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("机构类型（1：公司；2：部门；3：小组）")
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
    @ApiModelProperty("备注")
    private String remarks;

    private List<OfficeVo> children;
}
