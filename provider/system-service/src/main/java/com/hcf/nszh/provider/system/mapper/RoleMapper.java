package com.hcf.nszh.provider.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hcf.nszh.provider.system.api.vo.RoleDetailVO;
import com.hcf.nszh.provider.system.entity.RoleEntity;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@CacheNamespace(readWrite = true, flushInterval = 60000)
@Repository
public interface RoleMapper extends BaseMapper<RoleEntity> {


    RoleDetailVO getByName(@Param("name") String name, @Param("roleId") String roleId );

    RoleEntity getByEnname(RoleEntity role);

    RoleEntity getRoleById(String id);

    List<RoleDetailVO> listRole(Page<RoleDetailVO> page,@Param("search") String search);

    List<RoleDetailVO> listRole();

    List<RoleEntity> listPageRole(Pagination pagination);

    /**
     * 维护角色与菜单权限关系
     *
     * @param role
     * @return
     */
    int deleteRoleMenu(RoleEntity role);

    int insertRoleMenu(RoleEntity role);

    /**
     * 维护角色与公司部门关系
     *
     * @param role
     * @return
     */
    int deleteRoleOffice(RoleEntity role);


    boolean deleteById(String id);

    List<RoleEntity> selectRoleNameById(@Param("id") String id);

    RoleDetailVO getRoleByEnname(@Param("enname") String enname);

    List<RoleEntity> findList(@Param("userId") Long userId, @Param("loginName") String loginName,
                              @Param("delFlag") String delFlag, @Param("useable") String useable);

    int delete(String id);

    RoleDetailVO getByRoleId(String id);

    void update(RoleDetailVO roleDetailVO);

    List<RoleDetailVO> searchRole(Page<RoleDetailVO> page, @Param("condition") String condition);

    RoleDetailVO getExistByEnname(@Param("enname") String enname,@Param("roleId") String roleId);

    String getAutoCode();
}
