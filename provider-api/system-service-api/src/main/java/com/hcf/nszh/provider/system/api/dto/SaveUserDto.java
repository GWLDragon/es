package com.hcf.nszh.provider.system.api.dto;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @Author huangxiong
 * @Date 2019/7/6
 **/
@ApiModel(value = "新增用户传入信息")
@Data
public class SaveUserDto implements Serializable {
    private static final long serialVersionUID = -991874608611477397L;
    private Long userId;
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")

    @Pattern(regexp = "[^\u4e00-\u9fa5]+",message = "用户名不能包含汉字")
    @Size(min = 1,max = 20,message = "用户名长度为1~20个字符")
    private String loginName;
    @ApiModelProperty("用户密码")
    private String password ;
    @ApiModelProperty("机构ID")
    @NotNull(message = "机构ID不能为空")
    private String officeId;
    @Pattern(regexp = "[\u4e00-\u9fa5]+",message = "姓名必须是汉字")
    @ApiModelProperty("姓名")
    @NotBlank(message = "姓名不能为空")
    @Size(min = 1,max = 10,message = "姓名长度为1~10个字符")
    private String name;
    @ApiModelProperty("用户工号")
    private String number;
    @ApiModelProperty("用户邮箱")
    @Size(max = 30,message = "用户邮箱不能超出30个字符")
    private String email;
    @ApiModelProperty("用户电话")
    @Size(max = 11,message = "手机号码不能超出11个字符")
    private String phone;
    @ApiModelProperty("用户手机")
    private String mobile;
    @ApiModelProperty("用户头像")
    private String photo;
    @NotNull(message = "用户角色不能为空")
    @ApiModelProperty("用户角色")
    private List<String> roleIds = Lists.newArrayList();

    @ApiModelProperty("是否是新记录")
    private Boolean isNewRecord = true;

    @ApiModelProperty("是否可登录S")
    private String loginFlag;


}
