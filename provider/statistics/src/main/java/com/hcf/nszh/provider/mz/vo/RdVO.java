package com.hcf.nszh.provider.mz.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈热点〉<br>
 * 〈〉
 *
 * @author gwl
 * @date 2021/6/7 11:23
 * @since 1.0.0
 */
@Data
public class RdVO implements Serializable {

    @ExcelProperty(index = 0)
    @ApiModelProperty("序号")
    private Integer xh;

    @ExcelProperty(index = 1)
    @ApiModelProperty("热点问题")
    private String rdwt;

    @ExcelProperty(index = 2)
    @ApiModelProperty("热点问题数量")
    private Long rdwtsl;

    @ExcelProperty(index = 3)
    @ApiModelProperty("咨询热词")
    private String zxrc;

    @ExcelProperty(index = 4)
    @ApiModelProperty("咨询热词数量")
    private Long zxrcsl;

}
