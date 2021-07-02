package com.hcf.nszh.consumer.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.common.annotation.RequestLogging;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.provider.system.api.dto.OfficeIdDTO;
import com.hcf.nszh.provider.system.api.dto.OperatorOfficeDTO;
import com.hcf.nszh.provider.system.api.dto.SearchAddressDTO;
import com.hcf.nszh.provider.system.api.dto.SearchOfficeDTO;
import com.hcf.nszh.provider.system.api.service.OfficeApiService;
import com.hcf.nszh.provider.system.api.vo.AddressBookVo;
import com.hcf.nszh.provider.system.api.vo.OfficeDetailVO;
import com.hcf.nszh.provider.system.api.vo.OfficeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author hx
 * @Date 2019/7/22
 **/
@Api(value = "API - OfficeController", description = "机构管理")
@RequestMapping(value = "/office")
@RestController
public class OfficeController {
    @Autowired
    private OfficeApiService officeApiService;

    @ApiOperation("机构列表查询,use")
    @RequestLogging
    @GetMapping("/listOffice")
    public ResponseVo<List<OfficeVo>> listOffice() {

        ResponseVo<List<OfficeVo>> listResponseVo = officeApiService.listOffice();
        return listResponseVo;
    }

    @RequestLogging
    @PostMapping("/getOffice")
    public ResponseVo<OfficeVo> getOffice(@RequestBody OfficeIdDTO officeIdDTO) {

        ResponseVo<OfficeVo> office = officeApiService.getOffice(officeIdDTO);
        return office;
    }

    @ApiOperation("根据机构ID删除机构信息,use")
    @RequestLogging
    @PostMapping("deleteOfficeById")
    public ResponseVo<String> deleteOfficeById(@RequestBody OfficeIdDTO officeIdDTO) {
        ResponseVo<String> integerResponseVo = officeApiService.deleteOfficeById(officeIdDTO);
        return integerResponseVo;

    }

    @RequestLogging
    @PostMapping("getUserOfficeTree")
    public ResponseVo<List<OfficeVo>> getUserOfficeTree() {

        ResponseVo<List<OfficeVo>> userOfficeTree = officeApiService.getUserOfficeTree();
        return userOfficeTree;
    }

    @ApiOperation("添加/修改机构,use")
    @RequestLogging
    @PostMapping("operatorOffice")
    public ResponseVo<OfficeDetailVO> operatorOffice(@RequestBody OperatorOfficeDTO operatorOfficeDTO) {
        ResponseVo<OfficeDetailVO> officeDetailVOResponseVo = officeApiService.operatorOffice(operatorOfficeDTO);
        return officeDetailVOResponseVo;

    }

    @RequestLogging
    @PostMapping("/searchOffice")
    public ResponseVo<List<OfficeVo>> searchOffice(@RequestBody SearchOfficeDTO searchOfficeDTO) {

        ResponseVo<List<OfficeVo>> listResponseVo = officeApiService.searchOffice(searchOfficeDTO);
        return listResponseVo;
    }

    @ApiOperation("通讯录查询,use")
    @RequestLogging
    @PostMapping("/addressBook")
    public ResponseVo<Page<AddressBookVo>> addressBook(@RequestBody SearchAddressDTO searchAddressDTO) {
        ResponseVo<Page<AddressBookVo>> listResponseVo = officeApiService.addressBook(searchAddressDTO.getPageNum(),searchAddressDTO.getPageSize(),searchAddressDTO.getSearch(),searchAddressDTO.getOfficeIds());
        return listResponseVo;
    }

    @ApiOperation("根据用户ID查询用户的机构根ID,use")
    @GetMapping("getRootOfficeIdByUserId")
    public ResponseVo<String> getRootOfficeIdByUserId(@RequestParam("userId") Long userId) {
        return  officeApiService.getRootOfficeIdByUserId(userId);
    }

    @ApiOperation("机构名称查询,use")
    @RequestLogging
    @GetMapping(value = "/getOfficeByName")
    public ResponseVo<List<OfficeVo>> getOfficeByName(@ApiParam(value = "名称", required = true) @RequestParam(value = "name") String name,
                                                  @ApiParam(value = "上级机构", required = true) @RequestParam(value = "parentId") String parentId) {
        return  officeApiService.getOfficeByName(name,parentId);
    }

    @ApiOperation("机构编码查询,use")
    @RequestLogging
    @GetMapping(value = "/getOfficeByCode")
    public ResponseVo<List<OfficeVo>> getOfficeByCode(@ApiParam(value = "编码", required = true) @RequestParam(value = "code") String code,
                                                      @ApiParam(value = "上级机构", required = true) @RequestParam(value = "parentId") String parentId) {
        return  officeApiService.getOfficeByCode(code,parentId);
    }

    @ApiOperation("获取根机构,use")
    @RequestLogging
    @GetMapping("/getRootOffice")
    public ResponseVo<List<OfficeVo>> getRootOffice(){
        return  officeApiService.getRootOffice();
    }
}
