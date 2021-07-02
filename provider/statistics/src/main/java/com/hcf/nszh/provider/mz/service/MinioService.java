package com.hcf.nszh.provider.mz.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 〈一句话功能简述〉<br>
 * 〈minio文件上传下载删除〉
 *
 * @author gwl
 * @date 2021/6/10 14:40
 * @since 1.0.0
 */

public interface MinioService {

    /**
     * 下载
     *
     * @param file 流
     * @return
     */
    String uploadFileMinio(MultipartFile file);

    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    String removeMinio(String fileName);

    /**
     * 下载文件
     *
     * @param fileName
     * @return
     */
    InputStream downloadMinio(String fileName) throws Exception;
}
