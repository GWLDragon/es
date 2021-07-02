package com.hcf.nszh.provider.mz.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈服务情况〉<br>
 * 〈〉
 *
 * @author gwl
 * @date 2021/6/7 11:23
 * @since 1.0.0
 */
@Data
public class FwqkVO implements Serializable {

    @ExcelProperty(index = 0)
    @ApiModelProperty("日期")
    private String ny;

    @ExcelProperty(index = 1)
    @ApiModelProperty("服务人次语音")
    private Long fwrcyy;

    @ExcelProperty(index = 2)
    @ApiModelProperty("服务人次pc")
    private Long fwrcwl;

    @ExcelProperty(index = 3)
    @ApiModelProperty("服务人次微信")
    private Long fwrcwx;

    @ExcelProperty(index = 4)
    @ApiModelProperty("对话量语音")
    private Long dhlyy;

    @ExcelProperty(index = 5)
    @ApiModelProperty("对话量网络")
    private Long dhlwl;

    @ExcelProperty(index = 6)
    @ApiModelProperty("对话量微信")
    private Long dhlwx;

    public String getNy() {
        return ny;
    }

    public void setNy(String ny) {
        this.ny = ny.replace(".0","");
    }

    public Long getFwrcyy() {
        return fwrcyy;
    }

    public void setFwrcyy(Long fwrcyy) {
        this.fwrcyy = fwrcyy;
    }

    public Long getFwrcwl() {
        return fwrcwl;
    }

    public void setFwrcwl(Long fwrcwl) {
        this.fwrcwl = fwrcwl;
    }

    public Long getDhlyy() {
        return dhlyy;
    }

    public void setDhlyy(Long dhlyy) {
        this.dhlyy = dhlyy;
    }

    public Long getDhlwl() {
        return dhlwl;
    }

    public void setDhlwl(Long dhlwl) {
        this.dhlwl = dhlwl;
    }

    public Long getFwrcwx() {
        return fwrcwx;
    }

    public void setFwrcwx(Long fwrcwx) {
        this.fwrcwx = fwrcwx;
    }

    public Long getDhlwx() {
        return dhlwx;
    }

    public void setDhlwx(Long dhlwx) {
        this.dhlwx = dhlwx;
    }
}
