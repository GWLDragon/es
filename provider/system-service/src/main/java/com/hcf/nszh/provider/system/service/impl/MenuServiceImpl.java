package com.hcf.nszh.provider.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.hcf.nszh.common.constant.CacheConstant;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.common.exception.BusinessException;
import com.hcf.nszh.common.exception.ServiceException;
import com.hcf.nszh.common.security.shiro.utils.UserUtils;
import com.hcf.nszh.common.utils.StringUtils;
import com.hcf.nszh.common.utils.UUIDUtils;
import com.hcf.nszh.provider.system.api.dto.MenuDto;
import com.hcf.nszh.provider.system.api.dto.OperatorMenuDto;
import com.hcf.nszh.provider.system.api.dto.SearchByConditionDTO;
import com.hcf.nszh.provider.system.api.dto.UpdateMenuSortDto;
import com.hcf.nszh.provider.system.api.vo.MenuVo;
import com.hcf.nszh.provider.system.api.vo.OperatorMenuVo;
import com.hcf.nszh.provider.system.api.vo.UserVo;
import com.hcf.nszh.provider.system.mapper.MenuMapper;
import com.hcf.nszh.provider.system.entity.MenuEntity;
import com.hcf.nszh.provider.system.service.MenuService;
import com.hcf.nszh.provider.system.utils.ObjConvert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Furant 2019/7/4 23:50
 */

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public int deleteMenuById(MenuDto menuDto) {
        // 当一层菜单存在菜单有效信息时，该菜单不可删除。只有最底层菜单才可执行删除操作
        if (StringUtils.isNotBlank(menuDto.getMenuId())) {
            List<MenuVo> menuVos = menuMapper.getMenuByParentId(menuDto.getMenuId());
            if (menuVos != null && menuVos.size() > 0) {
                throw new BusinessException(ErrorEnum.CANNOT_DELETE);
            }
            menuMapper.deleteById(menuDto.getMenuId());

        }
        return 1;

    }

    @Override
    public List<MenuVo> listMenu() {
        List<MenuVo> menuVos = menuMapper.listRootMenu();
        if (CollectionUtils.isEmpty(menuVos)) {
            return new ArrayList<>(0);
        } else {
            return menuVos;
        }
    }

    @Override
    public MenuVo getMenuById(MenuDto menuDto) {
        if (StringUtils.isBlank(menuDto.getMenuId())) {
            throw new BusinessException(ErrorEnum.PARAMS_WRONG);
        }
        if (redisTemplate.hasKey(CacheConstant.CACHE_MENU_LIST + CacheConstant.CACHE_MENU_LIST + menuDto.getMenuId())) {
            return JSON.parseObject(
                    redisTemplate.opsForValue().get(CacheConstant.CACHE_MENU_LIST + CacheConstant.CACHE_MENU_LIST + menuDto.getMenuId()),
                    MenuVo.class);
        }

        MenuVo rootMenuById = menuMapper.getRootMenuById(menuDto.getMenuId());
        return rootMenuById;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    @Override
    public OperatorMenuVo operatorMenu(OperatorMenuDto operatorMenuDto) {
        //避免 NPE
        if (operatorMenuDto == null) {
            throw new ServiceException();
        }

        MenuEntity menuEntity = new MenuEntity();
        OperatorMenuVo operatorMenuVo = new OperatorMenuVo();
        // 默认父ID等于空或0时为一级菜单
        if (StringUtils.isBlank(operatorMenuDto.getParentId())) {
            operatorMenuDto.setParentId("0");
        }
        // 设置新的父节点串
        if (!"0".equals(operatorMenuDto.getParentId())) {
            OperatorMenuVo parentMenu = menuMapper.selectById(operatorMenuDto.getParentId());
            if (parentMenu != null) {
                operatorMenuVo.setParent(parentMenu);
                if (StringUtils.isBlank(parentMenu.getParentIds()) || "0".equals(parentMenu.getParentIds())) {
                    operatorMenuDto.setParentIds(parentMenu.getId());
                } else {
                    operatorMenuDto.setParentIds(parentMenu.getParentIds() + "," + parentMenu.getId());
                }
            } else {
                throw new BusinessException(ErrorEnum.MENU_NOT_EXIST);
            }
        } else {
            operatorMenuDto.setParentIds("0");
        }
        ObjConvert.copyProperties(operatorMenuDto, operatorMenuVo);

        String id = "0";
        if (StringUtils.isNotBlank(operatorMenuVo.getId())) {
            id = operatorMenuVo.getId();
        }
        OperatorMenuVo menu = menuMapper.selectById(id);

        // 保存或更新实体
        UserVo user = UserUtils.getUser();
        Long userId = user.getUserId();
        // 判断新增或修改的菜单名是否在数据库已存在，
        List<MenuEntity> existMean = menuMapper.getMenuIsExistByName(operatorMenuVo.getId(), operatorMenuVo.getName(), operatorMenuVo.getParentId());
        if (existMean != null && existMean.size() > 0) {
            throw new BusinessException(ErrorEnum.MENU_EXIST);
        }
        if (StringUtils.isBlank(operatorMenuDto.getId())) {
            if (operatorMenuDto.getSort() == null) {
                // 为空时默认为1
                operatorMenuVo.setSort(new BigDecimal(1));
            }
            if (StringUtils.isBlank(operatorMenuDto.getIsShow())) {
                // 为空时默认为1
                operatorMenuVo.setIsShow("1");
            }
            operatorMenuVo.setId(UUIDUtils.uuid());
            operatorMenuVo.setCreateBy(String.valueOf(userId));
            operatorMenuVo.setUpdateBy(String.valueOf(userId));
            operatorMenuVo.setCreateDate(new Date());
            operatorMenuVo.setUpdateDate(new Date());
            operatorMenuVo.setParentId(operatorMenuDto.getParentId());
            dozerBeanMapper.map(operatorMenuVo, menuEntity);
            if (StringUtils.isEmpty(menuEntity.getDelFlag())){
                menuEntity.setDelFlag("0");
            }
            menuMapper.insert(menuEntity);
            return operatorMenuVo;
        } else {
            operatorMenuVo.setUpdateBy(String.valueOf(userId));
            operatorMenuVo.setUpdateDate(new Date());
            dozerBeanMapper.map(operatorMenuVo, menuEntity);
            if (StringUtils.isBlank(menuEntity.getIcon())) {
                menuEntity.setIcon("");
            }
            if (StringUtils.isBlank(menuEntity.getRemarks())) {
                menuEntity.setRemarks("");
            }

            if (StringUtils.isBlank(menuEntity.getPermission())) {
                menuEntity.setPermission("");
            }
            menuMapper.updateByPrimaryKeySelective(menuEntity);

        }
        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = menu.getParentIds();
        // 更新子节点 parentIds
        OperatorMenuVo m = new OperatorMenuVo();
        m.setParentIds("%" + operatorMenuVo.getId() + "%");
        List<OperatorMenuVo> list = menuMapper.findByParentIdsLike(m);
        for (OperatorMenuVo e : list) {
            if (e != null) {
                if ("0".equals(operatorMenuVo.getParentId())) {
                    e.setParentIds(operatorMenuVo.getId());
                } else {
                    if ("0".equals(oldParentIds)) {
                        e.setParentIds(operatorMenuVo.getParentIds() + "," + e.getParentIds());
                    } else {
                        e.setParentIds(e.getParentIds().replace(oldParentIds, operatorMenuVo.getParentIds()));
                    }
                }
                menuMapper.updateParentIds(e);
            }
        }
        return operatorMenuVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<MenuVo> updateSort(List<UpdateMenuSortDto> updateMenuSortDto) {

        List<MenuVo> list = new ArrayList<>();
        updateMenuSortDto.forEach(o -> {
            MenuVo menuVo = new MenuVo();
            menuVo.setId(o.getMenuId());
            menuVo.setSort(o.getSort());
            menuMapper.updateSort(menuVo);
            list.add(menuVo);
        });
        return list;
    }

    @Override
    public List<MenuVo> getMenuByName(SearchByConditionDTO searchByConditionDTO) {

        if (StringUtils.isBlank(searchByConditionDTO.getName())) {
            List<MenuVo> menuVos = menuMapper.listRootMenu();
            return menuVos;
        }

        List<MenuVo> menuByName = menuMapper.getMenuByName(searchByConditionDTO.getName());

        return menuByName;
    }

    @Override
    public MenuVo getRootMenu() {
        MenuEntity menuEntity = menuMapper.selectRootMenu();
        MenuVo menuVo = new MenuVo();
        dozerBeanMapper.map(menuEntity, menuVo);
        return menuVo;
    }

    @Override
    public List<MenuEntity> getMenuByName(String name, String parentId) {
        List<MenuEntity> menuIsExistByName = null;
        try {
            menuIsExistByName = menuMapper.getMenuIsExistByName(null, name, parentId);
        }catch (Exception e){
        	log.error("service 添加菜单异常信息 = {}",e);
		}
        return menuIsExistByName;
    }
}
