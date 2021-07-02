package com.hcf.nszh.provider.mz.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈知识〉<br>
 * 〈〉
 *
 * @author gwl
 * @date 2021/6/7 11:23
 * @since 1.0.0
 */
@Data
public class ZsVO implements Serializable {

    @ExcelProperty(index = 0)
    @ApiModelProperty("知识类型")
    private String zslx;

    @ExcelProperty(index = 1)
    @ApiModelProperty("知识类别")
    private String zslb;

    @ExcelProperty(index = 2)
    @ApiModelProperty("标准问题数量")
    private Long bzwtsl;

    @ExcelProperty(index = 3)
    @ApiModelProperty("扩展问题数量")
    private Long kzwtsl;

}
