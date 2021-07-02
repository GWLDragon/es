package com.hcf.nszh.provider.mz.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈热点问题（数据展示平台）〉
 *
 * @author gwl
 * @date 2021/6/17 10:07
 * @since 1.0.0
 */
@Data
public class RdwtVO implements Serializable {
    @ExcelProperty(index = 0)
    @ApiModelProperty("序号")
    private Integer xh;

    @ExcelProperty(index = 1)
    @ApiModelProperty("热点问题")
    private String rdwt;
}
