package com.hcf.nszh.provider.system.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.provider.system.api.config.SystemFeignConfiguration;
import com.hcf.nszh.provider.system.api.dto.OfficeIdDTO;
import com.hcf.nszh.provider.system.api.dto.OperatorOfficeDTO;
import com.hcf.nszh.provider.system.api.dto.SearchOfficeDTO;
import com.hcf.nszh.provider.system.api.hystrix.OfficeApiServiceFeignHystrix;
import com.hcf.nszh.provider.system.api.vo.AddressBookVo;
import com.hcf.nszh.provider.system.api.vo.OfficeDetailVO;
import com.hcf.nszh.provider.system.api.vo.OfficeVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "system-service", path = "/office",
        configuration = SystemFeignConfiguration.class,
        fallback = OfficeApiServiceFeignHystrix.class)
@Service
public interface OfficeApiService {

    @GetMapping("/listOffice")
    ResponseVo<List<OfficeVo>> listOffice();

    @PostMapping("/getOffice")
    ResponseVo<OfficeVo> getOffice(@RequestBody OfficeIdDTO officeIdDTO);

    @PostMapping("operatorOffice")
    ResponseVo<OfficeDetailVO> operatorOffice(@RequestBody OperatorOfficeDTO operatorOfficeDTO);

    @PostMapping("deleteOfficeById")
    ResponseVo<String> deleteOfficeById(@RequestBody OfficeIdDTO officeIdDTO);

    @GetMapping("/treeData/{extId}/{type}/{grade}/{isAll}")
    ResponseVo<List<Map<String, Object>>> treeData(@PathVariable(value = "extId", required = false) String extId,
                                                   @PathVariable(value = "type", required = false) String type,
                                                   @PathVariable(value = "grade", required = false) Long grade,
                                                   @PathVariable(value = "isAll", required = false) Boolean isAll);

    @PostMapping("getUserOfficeTree")
    ResponseVo<List<OfficeVo>> getUserOfficeTree();

    @PostMapping("/searchOffice")
    ResponseVo<List<OfficeVo>> searchOffice(@RequestBody SearchOfficeDTO searchOfficeDTO);

    @PostMapping("/addressBook")
    ResponseVo<Page<AddressBookVo>> addressBook(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum,
                                                @RequestParam(required = false, defaultValue = "10", value = "pageSize") int pageSize,
                                                @RequestParam(required = false, value = "search") String search,
                                                @RequestParam(required = false, value = "officeIds") List<String> officeIds);

    @GetMapping("/getRootOffice")
    ResponseVo<List<OfficeVo>> getRootOffice();

    @GetMapping("/getRootOfficeById")
    ResponseVo<OfficeVo> getRootOfficeById(@RequestParam(required = true, value = "officeId") String officeId);

    /**
     * 根据用户ID查询用户的机构根ID
     *
     * @param userId
     * @return
     */
    @GetMapping("/getRootOfficeIdByUserId")
    ResponseVo<String> getRootOfficeIdByUserId(@RequestParam(required = true, value = "userId") Long userId);

    @GetMapping(value = "/getOfficeByName")
    ResponseVo<List<OfficeVo>> getOfficeByName(@RequestParam(value = "name") String name,
                                               @RequestParam(value = "parentId") String parentId);

    @GetMapping(value = "/getOfficeByCode")
    ResponseVo<List<OfficeVo>> getOfficeByCode(@RequestParam(value = "code") String code,
                                               @RequestParam(value = "parentId") String parentId);
}
