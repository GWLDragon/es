package com.hcf.nszh.provider.system.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Furant
 * 2019/7/23 18:19
 */
@Data
public class SearchAddressDTO implements Serializable {

    private Integer pageNum;
    private Integer pageSize;
    private String search;
    private List<String> officeIds;
}
