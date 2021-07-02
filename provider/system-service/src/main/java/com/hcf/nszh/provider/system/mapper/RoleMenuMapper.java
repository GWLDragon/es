package com.hcf.nszh.provider.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hcf.nszh.provider.system.entity.RoleMenuEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Furant
 * 2019/7/23 11:50
 */
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenuEntity> {

    int insert(@Param("menuIds") List<String> menuIds, @Param("roleId") String roleId);

    int deleteMenuFromRole(@Param("roleId") String roleId);

    List<RoleMenuEntity> getByRoleId(String roleId);
}
