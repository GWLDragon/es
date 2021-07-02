package com.hcf.nszh.provider.system.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Furant
 * 2019/7/15 15:52
 */
@Data
public class RoleDetailVO implements Serializable {

    private String roleId;
    private OfficeVo office;
    private String name;
    private String enname;
    private String roleType;
    private String dataScope;
    private String remarks;

    private String oldName;
    private String oldEnname;
    private String sysData;
    private String useable;
    private String createBy;
    private Date createDate;
    private String updateBy;
    private Date updateDate;

    private List<String> menuIds;

    private List<MenuVo> menuVos;

}
