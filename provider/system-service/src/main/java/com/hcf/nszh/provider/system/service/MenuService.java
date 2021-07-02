package com.hcf.nszh.provider.system.service;

import com.hcf.nszh.provider.system.api.dto.MenuDto;
import com.hcf.nszh.provider.system.api.dto.OperatorMenuDto;
import com.hcf.nszh.provider.system.api.dto.SearchByConditionDTO;
import com.hcf.nszh.provider.system.api.dto.UpdateMenuSortDto;
import com.hcf.nszh.provider.system.api.vo.MenuVo;
import com.hcf.nszh.provider.system.api.vo.OperatorMenuVo;
import com.hcf.nszh.provider.system.entity.MenuEntity;

import java.util.List;

/**
 * Created by Furant
 * 2019/7/4 18:08
 */
public interface MenuService {

    int deleteMenuById(MenuDto menuDto);

    List<MenuVo> listMenu();

    MenuVo getMenuById(MenuDto menuDto);

    OperatorMenuVo operatorMenu(OperatorMenuDto operatorMenuDto);

    List<MenuVo> updateSort(List<UpdateMenuSortDto> updateMenuSortDto);

    List<MenuVo> getMenuByName(SearchByConditionDTO searchByConditionDTO);

    MenuVo getRootMenu();

    List<MenuEntity> getMenuByName(String name, String parentId);
}
