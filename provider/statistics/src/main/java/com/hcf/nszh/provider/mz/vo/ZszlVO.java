package com.hcf.nszh.provider.mz.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈知识总量〉<br>
 * 〈〉
 *
 * @author gwl
 * @date 2021/6/7 11:23
 * @since 1.0.0
 */
@Data
public class ZszlVO implements Serializable {

    @ExcelProperty(index = 4)
    @ApiModelProperty("标准问题总计")
    private Long bzwthj;

    @ExcelProperty(index = 5)
    @ApiModelProperty("扩展问题总计")
    private Long kzwthj;

    @ExcelProperty(index = 6)
    @ApiModelProperty("同义词")
    private Long tyc;

    @ExcelProperty(index = 7)
    @ApiModelProperty("专有词")
    private Long zyc;

    @ExcelProperty(index = 8)
    @ApiModelProperty("口语词")
    private Long kyc;

    @ExcelProperty(index = 9)
    @ApiModelProperty("敏感词")
    private Long mgc;


}
