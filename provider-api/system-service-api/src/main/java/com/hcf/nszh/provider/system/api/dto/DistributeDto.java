package com.hcf.nszh.provider.system.api.dto;

import java.util.List;

public class DistributeDto {
    private List<String> UserId;
    private String roleId;
    private boolean flag;

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<String> getUserId() {
        return UserId;
    }

    public void setUserId(List<String> userId) {
        UserId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
