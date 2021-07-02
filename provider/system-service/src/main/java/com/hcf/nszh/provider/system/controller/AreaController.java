package com.hcf.nszh.provider.system.controller;

import com.hcf.nszh.common.annotation.RequestLogging;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.provider.system.service.AreaService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Furant
 * 2019/7/5 0:34
 */

@Slf4j
@RestController
@RequestMapping("/area")
@Api("AreaController 区域管理")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @RequestLogging
    @GetMapping(value = "/list")
    @ExceptionHandler(Exception.class)
    public ResponseVo deleteMenuById() {
        return new ResponseVo(ErrorEnum.SUCCESS, areaService.list());
    }
}
