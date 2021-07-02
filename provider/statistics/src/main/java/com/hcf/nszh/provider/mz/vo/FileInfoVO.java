package com.hcf.nszh.provider.mz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author gwl
 * @date 2021/6/17 14:11
 * @since 1.0.0
 */
@Data
public class FileInfoVO implements Serializable {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("文件名称")
    private String fileName;
    @ApiModelProperty("文件类型")
    private String type;
    @ApiModelProperty("文件大小")
    private Long size;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("创建人id")
    private Long userId;
    @ApiModelProperty("创建人名称")
    private String userName;
    @ApiModelProperty("文件存储路径")
    private String url;

    public FileInfoVO() {
    }

    public FileInfoVO(Long id, String fileName, String type, Long size, Date createTime, Long userId, String userName, String url) {
        this.id = id;
        this.fileName = fileName;
        this.type = type;
        this.size = size;
        this.createTime = createTime;
        this.userId = userId;
        this.userName = userName;
        this.url = url;
    }

    public FileInfoVO(String fileName, String type, Long size, Date createTime, Long userId, String userName, String url) {
        this.fileName = fileName;
        this.type = type;
        this.size = size;
        this.createTime = createTime;
        this.userId = userId;
        this.userName = userName;
        this.url = url;
    }
}
