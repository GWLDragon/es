package com.hcf.nszh.provider.mz.controller;

import com.hcf.nszh.common.annotation.RequestLogging;
import com.hcf.nszh.provider.mz.service.EsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
@RequestMapping("/es")
@Api(tags = "es", value = "es")
public class EsController {

    @Autowired
    private EsService esService;

    @RequestLogging
    @PostMapping("/addDocTest")
    @ApiOperation(value = "addDocTest")
    public void addDocTest() throws IOException {
        esService.addDocTest();
    }

    @RequestLogging
    @PostMapping("/updateDoc")
    @ApiOperation(value = "updateDoc")
    public void updateDoc() throws IOException {
        esService.updateDoc();
    }

    @RequestLogging
    @PostMapping("/deleteDoc")
    @ApiOperation(value = "deleteDoc")
    public void deleteDoc() throws IOException {
        esService.deleteDoc();
    }

    @Async
    @PostMapping("/insertToTest")
    @ApiOperation(value = "insertToTest")
    public void insertToTest(){
        esService.insertToTest();
    }
}
