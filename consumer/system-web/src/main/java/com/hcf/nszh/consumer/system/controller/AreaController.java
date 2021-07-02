package com.hcf.nszh.consumer.system.controller;

import com.hcf.nszh.common.annotation.RequestLogging;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.provider.system.api.service.AreaApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Furant
 * 2019/7/5 0:34
 */
@Api(value = "API - AreaController", description = "区域管理")
@RestController
@RequestMapping("/area")
public class AreaController {

    @Autowired
    private AreaApiService areaApiService;


    @ApiOperation("查询所有区域")
    @RequestLogging
    @GetMapping(value = "/list")
    public ResponseVo list() {
        return new ResponseVo(ErrorEnum.SUCCESS, areaApiService.list());

    }
}
