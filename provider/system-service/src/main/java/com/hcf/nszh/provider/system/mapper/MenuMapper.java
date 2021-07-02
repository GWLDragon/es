package com.hcf.nszh.provider.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hcf.nszh.provider.system.api.dto.OperatorMenuDto;
import com.hcf.nszh.provider.system.api.vo.MenuVo;
import com.hcf.nszh.provider.system.api.vo.OperatorMenuVo;
import com.hcf.nszh.provider.system.entity.MenuEntity;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@CacheNamespace(flushInterval = 60000)
@Repository
public interface MenuMapper extends BaseMapper<MenuEntity> {

    List<OperatorMenuVo> findByParentIdsLike(OperatorMenuVo menu);

    List<MenuEntity> findByUserId(MenuEntity menuEntity);

    int updateParentIds(OperatorMenuVo menu);

    int updateSort(MenuVo menuVo);

    MenuEntity selectRootMenu();

    List<MenuEntity> selectParentMenu(@Param("parentIds") List<String> parentIds);

    MenuEntity selectAMenu();

    int deleteById(String menuId);

    List<MenuEntity> listMenu();

    List<MenuEntity> getMenu();

    OperatorMenuVo selectById(String menuId);

    void update(OperatorMenuVo operatorMenuVo);

    List<OperatorMenuDto> listMenus();

    List<MenuEntity> listByIds(List<String> ids);

    List<MenuVo> listRootMenu();

    List<MenuVo> getMenuByParentId(@Param("parentId") String parentId);

    MenuVo getRootMenuById(String menuId);

    List<MenuVo> getMenuByName(@Param("condition") String condition);

    List<MenuEntity> findAllList(MenuEntity menuEntity);

    List<MenuVo> getMenuchildrenByParentId(@Param("parentId") String parentId);

    List<MenuEntity> getMenuIsExistByName(@Param("id") String id,@Param("name") String name,@Param("parentId") String parentId);

   int updateByPrimaryKeySelective(MenuEntity menuEntity);

    List<MenuVo> getMenuByRoleId(@Param("roleId") String roleId);
}
