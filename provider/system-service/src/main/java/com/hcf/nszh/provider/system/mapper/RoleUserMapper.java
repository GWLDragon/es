package com.hcf.nszh.provider.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.provider.system.api.dto.AssignRoleDTO;
import com.hcf.nszh.provider.system.api.vo.AssignedUserVO;
import com.hcf.nszh.provider.system.api.vo.RoleUserVO;
import com.hcf.nszh.provider.system.entity.RoleUserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Furant
 * 2019/7/23 11:50
 */
@Repository
public interface RoleUserMapper extends BaseMapper<RoleUserEntity> {

    int insert(@Param("assignRoleDTO") AssignRoleDTO assignRoleDTO);

    RoleUserEntity selectByRoleIdAndUserId(@Param("userId") Long userId, @Param("roleId") String roleId);

    List<RoleUserVO> selectByRoleId(@Param("roleId")String roleId);

    List<RoleUserVO> selectByUserId(@Param("userId") Long userId);

    int delete(@Param("roleId") String roleId);

    int deleteByUserId(Long userId);

    int deleteUserFromRole(@Param("userId") Long userId,@Param("roleId") String roleId);

    List<AssignedUserVO> getRoleUserPage(Page<AssignedUserVO> page);

    List<RoleUserEntity> listAll();

    List<Long> selectExistUserOfRole(@Param("userIds") List<Long> userIds,@Param("roleId") String roleId );
}
