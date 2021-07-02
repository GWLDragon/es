package com.hcf.nszh.provider.system.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Furant
 * 2019/7/22 10:35
 */
@Data
public class OfficeIdDTO implements Serializable {

    @ApiModelProperty("机构id")
    @NotNull(message = "机构ID不能为空")
    private String officeId;
}
