package com.hcf.nszh.provider.system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.provider.system.api.dto.*;
import com.hcf.nszh.provider.system.api.vo.AssignedUserVO;
import com.hcf.nszh.provider.system.api.vo.RoleDetailVO;

import java.util.List;

/**
 * Created by Furant
 * 2019/7/3 19:59
 */
public interface RoleService {

    /**
     * @param roleId
     * @return
     */
     int deleteRoleByRoleId( String roleId);

    /**
     * @param roleId
     * @return
     */
     RoleDetailVO getRoleByRoleId(String roleId);

    /**
     * @return
     */
     List<RoleDetailVO> listRoles();

    /**
     * @return
     */
     Page<RoleDetailVO> listPageRoles(Page<RoleDetailVO> page,String search);

    /**
     * @param operatorRoleDto
     */
     RoleDetailVO operatorRole(OperatorRoleDto operatorRoleDto);

     RoleDetailVO getRoleByEnname(String enname);

    RoleDetailVO getRoleByName(String name);

     Page<RoleDetailVO> searchRole(Page<RoleDetailVO> page, SearchByConditionDTO searchByConditionDTO);


     Integer assignRole(AssignRoleDTO assignRoleDTO);

     Integer deleteUserFromRole(DeleteUserFromRoleDTO deleteUserFromRoleDTO);

     Page<AssignedUserVO> listUserAssignedRole(ListAssignedUserDTO listAssignedUserDTO);

}
