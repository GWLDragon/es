package com.hcf.nszh.provider.system.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author huangxiong
 * @Date 2019/7/12
 **/
@Data
public class QueryUserPageDto implements Serializable {
    private String userName;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String officeId;
}
