package com.hcf.nszh.consumer.system.controller;

import com.hcf.nszh.common.annotation.RequestLogging;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.provider.system.api.dto.MenuDto;
import com.hcf.nszh.provider.system.api.dto.OperatorMenuDto;
import com.hcf.nszh.provider.system.api.dto.SearchByConditionDTO;
import com.hcf.nszh.provider.system.api.dto.UpdateMenuSortDto;
import com.hcf.nszh.provider.system.api.service.MenuApiService;
import com.hcf.nszh.provider.system.api.vo.MenuVo;
import com.hcf.nszh.provider.system.api.vo.OperatorMenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author  Furant
 * 2019/7/5 0:34
 */
@Api(value = "API - MenuController", description = "菜单管理")
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuApiService menuApiService;


    @ApiOperation("根据菜单ID删除菜单,use")
    @RequestLogging
    @GetMapping(value = "/deleteMenuById")
    public ResponseVo deleteMenuById(@RequestParam(value = "menuId") String menuId) {
        return new ResponseVo(ErrorEnum.SUCCESS, menuApiService.deleteMenuById(menuId));

    }

    @ApiOperation("根据菜单ID获取菜单信息,use")
    @RequestLogging
    @RequestMapping(value = "/getMenuById", method = RequestMethod.POST)
    public ResponseVo<MenuVo> getMenuById(@RequestBody MenuDto menuDto) {
        ResponseVo<MenuVo> menuById = menuApiService.getMenuById(menuDto);
        return new ResponseVo(ErrorEnum.SUCCESS, menuById);
    }

    @ApiOperation("菜单列表,use")
    @RequestLogging
    @RequestMapping(value = "/listMenu", method = RequestMethod.GET)
    public ResponseVo<List<MenuVo>> listMenu() {
        ResponseVo<List<MenuVo>> listResponseVo = menuApiService.listMenu();
        return listResponseVo;
    }

    @ApiOperation("菜单的新增或修改,use")
    @RequestLogging
    @RequestMapping(value = "/operatorMenu", method = RequestMethod.POST)
    public ResponseVo<OperatorMenuVo> operatorMenu(@RequestBody OperatorMenuDto operatorMenuDto) {
        ResponseVo<OperatorMenuVo> menuVoResponseVo = menuApiService.operatorMenu(operatorMenuDto);
        return menuVoResponseVo;

    }
    @ApiOperation("修改菜单排序")
    @RequestLogging
    @RequestMapping(value = "/updateSort", method = RequestMethod.POST)
    public ResponseVo<List<OperatorMenuVo>> updateSort(@RequestBody UpdateMenuSortDto updateMenuSortDto) {
        ResponseVo<List<OperatorMenuVo>> listResponseVo = menuApiService.updateSort(updateMenuSortDto);
        return listResponseVo;
    }

    @ApiOperation("菜单名称模糊查询")
    @RequestLogging
    @PostMapping("/searchMenu")
    public ResponseVo<List<MenuVo>> searchMenu(@RequestBody SearchByConditionDTO searchByConditionDTO) {
        return menuApiService.searchMenu(searchByConditionDTO);
    }

    @ApiOperation("菜单名称查询,use")
    @RequestLogging
    @GetMapping(value = "/getMenuByName")
    public ResponseVo<List<MenuVo>> getMenuByName(@ApiParam(value = "名称", required = true) @RequestParam(value = "name") String name,
                                            @ApiParam(value = "上级菜单", required = true) @RequestParam(value = "parentId") String parentId) {
        return  menuApiService.getMenuByName(name,parentId);
    }
}
