package com.hcf.nszh.provider.mz.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.common.annotation.RequestLogging;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.provider.mz.service.NsfwService;
import com.hcf.nszh.provider.mz.vo.FileInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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
@RequestMapping("/nsfw")
@Api(tags = "12366纳税服务", value = "nsfw")
public class NsfwController {

    @Autowired
    private NsfwService nsfwService;

    @RequestLogging
    @PostMapping("/importData")
    @ApiOperation(value = "导入数据")
    public ResponseVo<List<String>> importData(@RequestParam MultipartFile file) {

        List<String> list = nsfwService.importData(file);
        return new ResponseVo<>(ErrorEnum.SUCCESS, list);
    }

    @RequestLogging
    @GetMapping("/fwqd")
    @ApiOperation(value = "服务渠道")
    public ResponseVo<List<Map<String, Object>>> fwqd() {
        return nsfwService.fwqd();
    }

    @GetMapping("/fwqs")
    @ApiOperation(value = "服务趋势")
    @RequestLogging
    public ResponseVo<List<Map<String, Object>>> fwqs() {
        return nsfwService.fwqs();
    }

    @GetMapping("/rdwt")
    @ApiOperation(value = "热点问题")
    @RequestLogging
    public ResponseVo<List<Map<String, Object>>> rdwt() {
        return nsfwService.rdwt();
    }

    @GetMapping("/zxrc")
    @ApiOperation(value = "咨询热词")
    @RequestLogging
    public ResponseVo<List<Map<String, Object>>> zxrc() {
        return nsfwService.zxrc();
    }

    @GetMapping("/zslb")
    @ApiOperation(value = "知识类别")
    public ResponseVo<List<Map<String, Object>>> zslb() {
        return nsfwService.zslb();
    }

    @GetMapping("/zszl")
    @ApiOperation(value = "知识总量")
    @RequestLogging
    public ResponseVo<List<Map<String, Object>>> zszl() {
        return nsfwService.zszl();
    }

    @GetMapping("/zslx")
    @ApiOperation(value = "知识类型")
    @RequestLogging
    public ResponseVo<List<Map<String, Object>>> zslx() {
        return nsfwService.zslx();
    }

    @GetMapping("/hj")
    @ApiOperation(value = "合计")
    @RequestLogging
    public ResponseVo<List<Map<String, Object>>> hj() {
        return nsfwService.hj();
    }

    @GetMapping("/sjptRdwt")
    @ApiOperation(value = "热点问题（数据展示平台)")
    @RequestLogging
    public ResponseVo<List<Map<String, Object>>> sjptRdwt() {
        return nsfwService.sjptRdwt();
    }

    @GetMapping("/sjwh")
    @ApiOperation(value = "数据维护")
    @RequestLogging
    public ResponseVo<Page<FileInfoVO>> sjwh(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return new ResponseVo<>(ErrorEnum.SUCCESS, nsfwService.fileInfoQueryPage(pageNum, pageSize));
    }

    @GetMapping("/znkf")
    @ApiOperation(value = "12366智能客服")
    @RequestLogging
    public ResponseVo<List<Map<String, Object>>> znkf() {
        return nsfwService.znkf();
    }

    @GetMapping("/fwqdzn")
    @ApiOperation(value = "服务渠道智能")
    @RequestLogging
    public ResponseVo<Long> fwqdzn() {
        return nsfwService.fwqdzn();
    }
}
