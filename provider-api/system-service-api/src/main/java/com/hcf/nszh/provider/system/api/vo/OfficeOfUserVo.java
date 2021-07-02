package com.hcf.nszh.provider.system.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author huangxiong
 * @Date 2019/6/29
 **/
@Data
public class OfficeOfUserVo {
    private String id;

    private String parentIds;
    @ApiModelProperty("机构编码")
    private String code;
    @ApiModelProperty("机构名称")
    private String name;
    private String parentId;
    @ApiModelProperty("机构等级（1：一级；2：二级；3：三级；4：四级）")
    private String grade;
    @ApiModelProperty("电话")
    private String phone;
    private List<OfficeOfUserVo> children;
    private List<UserBaseVo> userBaseVos;
    private String roleId;
}
