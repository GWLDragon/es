package com.hcf.nszh.provider.system.controller;

import com.hcf.nszh.common.annotation.RequestLogging;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.provider.system.api.dto.OperatorMenuDto;
import com.hcf.nszh.provider.system.api.dto.SearchByConditionDTO;
import com.hcf.nszh.provider.system.api.vo.OperatorMenuVo;
import com.hcf.nszh.provider.system.api.dto.MenuDto;
import com.hcf.nszh.provider.system.api.dto.UpdateMenuSortDto;
import com.hcf.nszh.provider.system.api.vo.MenuVo;
import com.hcf.nszh.provider.system.entity.MenuEntity;
import com.hcf.nszh.provider.system.service.MenuService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Furant
 * 2019/7/5 0:34
 */

@Slf4j
@RestController
@RequestMapping("/menu")
@Api("MenuController 菜单管理")
public class MenuController {

    @Autowired
    private MenuService menuService;


    @RequestLogging
    @GetMapping(value = "/deleteMenuById")
    @ExceptionHandler(Exception.class)
    public ResponseVo deleteMenuById(@RequestParam(value = "menuId") String menuId) {
        MenuDto menuDto = new MenuDto();
        menuDto.setMenuId(menuId);
        return new ResponseVo(ErrorEnum.SUCCESS, menuService.deleteMenuById(menuDto));
    }

    @RequestLogging
    @RequestMapping(value = "/getMenuById", method = RequestMethod.POST)
    @ExceptionHandler(Exception.class)
    public ResponseVo<MenuVo> getMenuById(@RequestBody MenuDto menuDto) {
        return new ResponseVo(ErrorEnum.SUCCESS, menuService.getMenuById(menuDto));
    }

    @RequestLogging
    @RequestMapping(value = "/listMenu", method = RequestMethod.GET)
    @ExceptionHandler(Exception.class)
    public ResponseVo<List<MenuVo>> listMenu() {
        return new ResponseVo(ErrorEnum.SUCCESS, menuService.listMenu());
    }

    @RequestLogging
    @RequestMapping(value = "/operatorMenu", method = RequestMethod.POST)
    @ExceptionHandler(Exception.class)
    public ResponseVo<OperatorMenuVo> operatorMenu(@RequestBody OperatorMenuDto operatorMenuDto) {
        OperatorMenuVo operatorMenuVo = menuService.operatorMenu(operatorMenuDto);
        return new ResponseVo(ErrorEnum.SUCCESS, operatorMenuVo);
    }

    @RequestLogging
    @RequestMapping(value = "/updateSort", method = RequestMethod.POST)
    @ExceptionHandler(Exception.class)
    public ResponseVo<List<OperatorMenuVo>> updateSort(@RequestBody List<UpdateMenuSortDto> updateMenuSortDto) {
        return new ResponseVo(ErrorEnum.SUCCESS, menuService.updateSort(updateMenuSortDto));
    }

    @RequestLogging
    @PostMapping("/searchMenu")
    @ExceptionHandler(Exception.class)
    public ResponseVo<List<MenuVo>> searchMenu(@RequestBody SearchByConditionDTO searchByConditionDTO) {
        List<MenuVo> menuByName = null;
        try {
           menuByName = menuService.getMenuByName(searchByConditionDTO);
        }catch (Exception e){
            log.error("添加菜单======异常={}",e);
        }

        return new ResponseVo<>(ErrorEnum.SUCCESS, menuByName);
    }

    @RequestLogging
    @GetMapping(value = "/getMenuByName")
    @ExceptionHandler(Exception.class)
    public ResponseVo<List<MenuEntity>> getMenuByName(@RequestParam(value = "name") String name,
                                                      @RequestParam(value = "parentId") String parentId) {
        List<MenuEntity> menuByName = null;
        try {

            menuByName = menuService.getMenuByName(name, parentId);
        }catch (Exception e){
            log.error("查询菜单======异常={}",e);
        }
        return new ResponseVo<>(ErrorEnum.SUCCESS, menuByName);
    }
}
