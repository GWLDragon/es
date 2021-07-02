package com.hcf.nszh.provider.mz.service.impl;

import com.hcf.nszh.provider.mz.service.MinioService;
import com.hcf.nszh.provider.mz.utils.minio.MinioProperties;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author gwl
 * @date 2021/6/10 14:40
 * @since 1.0.0
 */

@Slf4j
@Service
public class MinioServiceImpl implements MinioService {

    @Autowired
    private MinioProperties minioProperties;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String uploadFileMinio(MultipartFile file) {

        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            //获取文件类型
            String type = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //文件上传到minio上的Name把文件后缀带上，不然下载出现格式问题
            String fileName = UUID.randomUUID() + "." + type;

            //创建头部信息
            Map<String, String> headers = new HashMap<>(1 << 2);
            //添加自定义内容类型
            headers.put("Content-Type", "application/octet-stream");
            //添加存储类
            headers.put("X-Amz-Storage-Class", "REDUCED_REDUNDANCY");
            //添加自定义/用户元数据
            Map<String, String> userMetadata = new HashMap<>(1 << 2);
            userMetadata.put("My-Project", "Project One");
            //判断桶存在否 不存在创建
            createBucket();
            //上传
            minioClient().putObject(
                    PutObjectArgs.builder().bucket(minioProperties.getBucket()).object(fileName).stream(
                            inputStream, inputStream.available(), -1)
                            .headers(headers)
                            .userMetadata(userMetadata)
                            .build());
            inputStream.close();
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败" + e.getMessage();
        }


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String removeMinio(String fileName) {
        try {
            minioClient().removeObject(RemoveObjectArgs.builder().bucket(minioProperties.getBucket()).object(fileName).build());
            return "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return "删除失败";
        }

    }

    @Override
    public InputStream downloadMinio(String fileName) throws Exception {
        return minioClient().getObject(GetObjectArgs.builder().bucket(minioProperties.getBucket()).object(fileName).build());
    }

    /**
     * 判断桶是否存在 不存在创建
     *
     * @throws Exception
     */
    private void createBucket() throws Exception {

        BucketExistsArgs exist = BucketExistsArgs.builder().bucket(minioProperties.getBucket()).build();
        boolean result = minioClient().bucketExists(exist);
        if (!result) {
            MakeBucketArgs create = MakeBucketArgs.builder().bucket(minioProperties.getBucket()).build();
            minioClient().makeBucket(create);
        }

    }

    /**
     * 创建连接
     *
     * @return
     */
    private MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioProperties.getUrl())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }
}
