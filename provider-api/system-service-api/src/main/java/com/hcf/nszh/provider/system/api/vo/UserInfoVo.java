package com.hcf.nszh.provider.system.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author huangxiong
 * @Date 2019/7/11
 **/
@Data
public class UserInfoVo implements Serializable {
    private List<MenuVo> menu;
    private MenuVo root;
    private UserVo user;
    private String photo;
}
