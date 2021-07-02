package com.hcf.nszh.provider.system.api.vo;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author huangxiong
 * @Date 2019/6/29
 **/
@ApiModel(value = "用户返回信息")
@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;

    @ApiModelProperty("机构")
    private String officeId;

    private OfficeVo office;

    private AreaVo area;

    @ApiModelProperty("顶级机构ID")
    private String rootOfficeId;
    @ApiModelProperty("顶级机构名称")
    private String rootOfficeName;
    @ApiModelProperty("登录名")
    private String loginName;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("工号")
    private String no;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("手机")
    private String mobile;
    @ApiModelProperty("用户类型")
    private String userType;
    @ApiModelProperty("最后登陆IP")
    private String loginIp;
    @ApiModelProperty("最后登陆日期")
    private Date loginDate;
    private String loginFlag;
    @ApiModelProperty("头像")
    private String photo;
    private boolean admin;

    @ApiModelProperty("皮肤")
    private String skin;
    @ApiModelProperty("系统皮肤")
    private String sysSkin;

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    private String oldLoginName;
    private String oldLoginIp;
    private Date oldLoginDate;
    private RoleVo role;
    @ApiModelProperty("拥有角色列表")
    private List<RoleVo> roleList = Lists.newArrayList();
    protected String createUserName;
    protected Date createDate;
    protected String updateUserName;
    protected Date updateDate;
    @ApiModelProperty("删除标记（0：正常；1：删除；2：审核）")
    protected String delFlag;
    @ApiModelProperty("菜单")
    private List<MenuVo> menuVoList;
    @ApiModelProperty("权限标识")
    private List<String> permissionList;
    @ApiModelProperty(notes = "请求唯一标识", hidden = true)
    private String uuid;
}
