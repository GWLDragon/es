package com.hcf.nszh.provider.mz.controller;

import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.provider.mz.service.MinioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author gwl
 * @date 2021/5/27 15:45
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/minio")
@Api(tags = "minio文件上传", value = "minio")
public class MinioController {

    @Autowired
    private MinioService minioService;


//    @PostMapping("/uploadFileMinio")
//    @ApiOperation(value = "上传")
    public ResponseVo<String> uploadFileMinio(@RequestParam("file") MultipartFile file) {

        return new ResponseVo<>(ErrorEnum.SUCCESS, minioService.uploadFileMinio(file));

    }

//    @GetMapping("/removeMinio")
//    @ApiOperation(value = "删除")
    public ResponseVo<String> removeMinio(@RequestParam("fileName") String fileName) {

        return new ResponseVo<>(ErrorEnum.SUCCESS, minioService.removeMinio(fileName));

    }

    @GetMapping("/downloadMinio")
    @ApiOperation(value = "下载")
    public void downloadMinio(@RequestParam("fileName") String fileName, HttpServletResponse response) throws Exception {

        InputStream stream = minioService.downloadMinio(fileName);
        byte[] bf = new byte[1024];
        int length;
        response.reset();
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        OutputStream outputStream = response.getOutputStream();
        // 输出文件
        while ((length = stream.read(bf)) > 0) {
            outputStream.write(bf, 0, length);
        }
        // 关闭输出流
        outputStream.close();

    }

}
