package com.hcf.nszh.provider.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.common.annotation.RequestLogging;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.provider.system.api.dto.SearchOfficeDTO;
import com.hcf.nszh.provider.system.api.vo.OfficeDetailVO;
import com.hcf.nszh.provider.system.api.vo.OfficeVo;
import com.hcf.nszh.provider.system.api.dto.OfficeIdDTO;
import com.hcf.nszh.provider.system.api.dto.OperatorOfficeDTO;
import com.hcf.nszh.provider.system.api.vo.AddressBookVo;
import com.hcf.nszh.provider.system.service.OfficeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Furant
 * 2019/7/22 14:39
 */
@Slf4j
@RestController
@RequestMapping("/office")
@Api("部门接口的相关操作")
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    @RequestLogging
    @GetMapping("/listOffice")
    @ExceptionHandler(Exception.class)
    public ResponseVo<List<OfficeVo>> listOffice() {

        List<OfficeVo> officeVos = officeService.listOffice();
        return new ResponseVo<>(ErrorEnum.SUCCESS, officeVos);
    }

    @RequestLogging
    @PostMapping("/getOffice")
    @ExceptionHandler(Exception.class)
    public ResponseVo<OfficeVo> getOffice(@RequestBody OfficeIdDTO officeIdDTO) {

        OfficeVo officeById = officeService.getOfficeById(officeIdDTO);
        return new ResponseVo<>(ErrorEnum.SUCCESS, officeById);
    }

    @RequestLogging
    @PostMapping("deleteOfficeById")
    @ExceptionHandler(Exception.class)
    public ResponseVo<String> deleteOfficeById(@RequestBody OfficeIdDTO officeIdDTO) {
        return new ResponseVo<>(ErrorEnum.SUCCESS, officeService.deleteOfficeById(officeIdDTO));
    }

    @RequestLogging
    @PostMapping("getUserOfficeTree")
    @ExceptionHandler(Exception.class)
    public ResponseVo<List<OfficeVo>> getUserOfficeTree() {

        List<OfficeVo> currentUserOfficeTree = officeService.getCurrentUserOfficeTree();
        return new ResponseVo<>(ErrorEnum.SUCCESS, currentUserOfficeTree);
    }

    @RequestLogging
    @PostMapping("operatorOffice")
    @ExceptionHandler(Exception.class)
    public ResponseVo<OfficeDetailVO> operatorOffice(@RequestBody OperatorOfficeDTO operatorOfficeDTO) {
        OfficeDetailVO officeDetailVO = officeService.operatorOffice(operatorOfficeDTO);
        log.info("返回===========结果{}", officeDetailVO);
        return new ResponseVo<>(ErrorEnum.SUCCESS, officeDetailVO);

    }

    @RequestLogging
    @PostMapping("/searchOffice")
    @ExceptionHandler(Exception.class)
    public ResponseVo<List<OfficeVo>> searchOffice(@RequestBody SearchOfficeDTO searchOfficeDTO) {
        return new ResponseVo<>(ErrorEnum.SUCCESS, officeService.searchOffice(searchOfficeDTO));
    }

    @PostMapping("/addressBook")
    @ExceptionHandler(Exception.class)
    public ResponseVo<Page<AddressBookVo>> addressBook(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum,
                                                       @RequestParam(required = false, defaultValue = "10", value = "pageSize") int pageSize,
                                                       @RequestParam(required = false, value = "search") String search,
                                                       @RequestParam(required = false, value = "officeIds") List<String> officeIds) {
        return new ResponseVo<>(ErrorEnum.SUCCESS, officeService.addressBook(pageNum, pageSize, search, officeIds));
    }

    @GetMapping("/getRootOffice")
    @ExceptionHandler(Exception.class)
    public ResponseVo<List<OfficeVo>> getRootOffice() {
        return new ResponseVo<>(ErrorEnum.SUCCESS, officeService.getRootOffice());
    }

    @GetMapping("/getRootOfficeById")
    @ExceptionHandler(Exception.class)
    public ResponseVo<OfficeVo> getRootOfficeById(@RequestParam(value = "officeId") String officeId) {
        return new ResponseVo<>(ErrorEnum.SUCCESS, officeService.getRootOfficeById(officeId));
    }

    @GetMapping("/getRootOfficeIdByUserId")
    @ExceptionHandler(Exception.class)
    public ResponseVo<String> getRootOfficeIdByUserId(@RequestParam(value = "userId") Long userId) {
        return new ResponseVo<>(ErrorEnum.SUCCESS, officeService.getRootOfficeIdByUserId(userId));
    }

    @RequestLogging
    @GetMapping(value = "/getOfficeByName")
    @ExceptionHandler(Exception.class)
    public ResponseVo<List<OfficeVo>> getOfficeByName(@RequestParam(value = "name") String name,
                                                      @RequestParam(value = "parentId") String parentId) {
        return new ResponseVo<>(ErrorEnum.SUCCESS, officeService.getOfficeByName(name, parentId));
    }

    @GetMapping(value = "/getOfficeByCode")
    @ExceptionHandler(Exception.class)
    public ResponseVo<List<OfficeVo>> getOfficeByCode(@RequestParam(value = "code") String code,
                                                      @RequestParam(value = "parentId") String parentId) {
        return new ResponseVo<>(ErrorEnum.SUCCESS, officeService.getOfficeByCode(code, parentId));
    }
}
