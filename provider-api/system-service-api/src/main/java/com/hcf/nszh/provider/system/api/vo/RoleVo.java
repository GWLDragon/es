package com.hcf.nszh.provider.system.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author huangxiong
 * @Date 2019/6/29
 **/
@Data
public class RoleVo implements Serializable {

    private String roleId;
    private String roleName;
    private String name;
    private String enname;
    private List<MenuVo> menuVos;
}
