package com.hcf.nszh.provider.system.api.vo;

import lombok.Data;

import java.util.List;


/**
 * @Author ygr
 * @Date 2019/9/2
 **/
@Data
public class AddressBookVo {
    private Long userId;
    private String OfficeId;
    private String OfficeName;
    private String name;
    private String phone;
    private List<String> roleNames;

}
