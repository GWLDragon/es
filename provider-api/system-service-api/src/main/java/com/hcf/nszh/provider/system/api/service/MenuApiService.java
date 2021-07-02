package com.hcf.nszh.provider.system.api.service;

import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.provider.system.api.config.SystemFeignConfiguration;
import com.hcf.nszh.provider.system.api.dto.MenuDto;
import com.hcf.nszh.provider.system.api.dto.OperatorMenuDto;
import com.hcf.nszh.provider.system.api.dto.SearchByConditionDTO;
import com.hcf.nszh.provider.system.api.dto.UpdateMenuSortDto;
import com.hcf.nszh.provider.system.api.hystrix.MenuServiceFeignHystrix;
import com.hcf.nszh.provider.system.api.vo.MenuVo;
import com.hcf.nszh.provider.system.api.vo.OperatorMenuVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author hcf
 */
@FeignClient(value = "system-service", path = "/menu",
        configuration = SystemFeignConfiguration.class,
        fallback = MenuServiceFeignHystrix.class)
@Service
public interface MenuApiService {
    @GetMapping(value = "/deleteMenuById")
    ResponseVo deleteMenuById(@RequestParam(value = "menuId") String menuId);

    @PostMapping(value = "/getMenuById")
    ResponseVo<MenuVo> getMenuById(@RequestBody MenuDto menuDto);

    @GetMapping(value = "/listMenu")
    ResponseVo<List<MenuVo>> listMenu();

    @PostMapping(value = "/operatorMenu")
    ResponseVo<OperatorMenuVo> operatorMenu(@RequestBody OperatorMenuDto operatorMenuDto);

    @PostMapping(value = "/updateSort")
    ResponseVo<List<OperatorMenuVo>> updateSort(@RequestBody UpdateMenuSortDto updateMenuSortDto);

    @PostMapping("/searchMenu")
    ResponseVo<List<MenuVo>> searchMenu(@RequestBody SearchByConditionDTO searchByConditionDTO);

    @GetMapping(value = "/getMenuByName")
    ResponseVo<List<MenuVo>> getMenuByName(@RequestParam(value = "name") String name,
                                           @RequestParam(value = "parentId") String parentId);
}
