package com.hcf.nszh.provider.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.common.constant.SystemConstant;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.common.exception.BusinessException;
import com.hcf.nszh.common.security.shiro.utils.UserUtils;
import com.hcf.nszh.common.utils.StringUtils;
import com.hcf.nszh.common.utils.UUIDUtils;
import com.hcf.nszh.provider.system.api.dto.*;
import com.hcf.nszh.provider.system.api.vo.AssignedUserVO;
import com.hcf.nszh.provider.system.api.vo.MenuVo;
import com.hcf.nszh.provider.system.api.vo.RoleDetailVO;
import com.hcf.nszh.provider.system.api.vo.UserVo;
import com.hcf.nszh.provider.system.entity.RoleEntity;
import com.hcf.nszh.provider.system.entity.RoleMenuEntity;
import com.hcf.nszh.provider.system.entity.RoleUserEntity;
import com.hcf.nszh.provider.system.mapper.*;
import com.hcf.nszh.provider.system.service.RoleService;
import com.hcf.nszh.provider.system.utils.ObjConvert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Furant
 * 2019/7/3 22:31
 */

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    private RoleUserMapper roleUserMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuMapper menuMapper;


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT,
            timeout = 36000, rollbackFor = Exception.class)
    @Override
    public int deleteRoleByRoleId(String roleId) {
        if (StringUtils.isBlank(roleId)) {
            throw new BusinessException(ErrorEnum.PARAMS_WRONG);
        }

        RoleDetailVO byRoleId = roleMapper.getByRoleId(roleId);
        if (byRoleId == null) {
            throw new BusinessException(ErrorEnum.NOT_EXIST_ROLE);
        }

        roleMapper.delete(roleId);
        roleUserMapper.delete(roleId);
        roleMenuMapper.deleteMenuFromRole(roleId);

        return 1;
    }

    @Override
    public RoleDetailVO getRoleByRoleId(String roleId) {
        if (StringUtils.isBlank(roleId)) {
            throw new BusinessException(ErrorEnum.PARAMS_WRONG);
        }
        //根据角色ID查询角色信息
        RoleDetailVO byRoleId = roleMapper.getByRoleId(roleId);
        if (byRoleId == null) {
            return new RoleDetailVO();
        }

        //获取角色的菜单
        List<MenuVo> menuVos = menuMapper.getMenuByRoleId(roleId);
        if (menuVos != null && menuVos.size() > 0) {
            byRoleId.setMenuVos(menuVos);
        }
        return byRoleId;
    }

    @Override
    public List<RoleDetailVO> listRoles() {

        List<RoleDetailVO> listRoles = roleMapper.listRole();
        if (listRoles.isEmpty()) {
            return new ArrayList<>(0);
        } else {

            return listRoles;
        }

    }

    @Override
    public Page<RoleDetailVO> listPageRoles(Page<RoleDetailVO> page, String search) {
        List<RoleDetailVO> roleDetailVOS = roleMapper.listRole(page, search);
        if (CollectionUtils.isEmpty(roleDetailVOS)) {
            return null;
        }
        page.setRecords(roleDetailVOS);
        return page;
    }

    /**
     * 生成规则编号:十位编号（从1开始，不够前补0）
     *
     * @param equipmentNo 最新编号
     * @return
     */
    public static String getNewEquipmentNo(String equipmentNo) {
        String newEquipmentNo = "";
        int newEquipment = Integer.parseInt(equipmentNo) + 1;
        newEquipmentNo = String.format("%010d", newEquipment);
        return newEquipmentNo;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT,
            timeout = 36000, rollbackFor = Exception.class)
    @Override
    public RoleDetailVO operatorRole(OperatorRoleDto operatorRoleDto) {
        RoleDetailVO roleDetailVO = new RoleDetailVO();
        if (StringUtils.isBlank(operatorRoleDto.getUseable())) {
            operatorRoleDto.setUseable(SystemConstant.YES);
        }
        ObjConvert.copyProperties(operatorRoleDto, roleDetailVO);


        //查询角色名称是否重名
        RoleDetailVO roleByName = roleMapper.getByName(operatorRoleDto.getName(), operatorRoleDto.getRoleId());
        if (roleByName != null) {
            throw new BusinessException(ErrorEnum.NAME_EXIST);
        }
        //如果编码不存在则自动生成编码
        String equipmentNo;
        if (StringUtils.isBlank(operatorRoleDto.getEnname())) {
            //查询自动生成的已有编码
            String code = roleMapper.getAutoCode();
            if (StringUtils.isBlank(code)) {
                equipmentNo = getNewEquipmentNo("3");
            } else {
                equipmentNo = getNewEquipmentNo(code);
            }
        } else {
            equipmentNo = operatorRoleDto.getEnname();
        }
        operatorRoleDto.setEnname(equipmentNo);
        roleDetailVO.setEnname(equipmentNo);
        //查询角色编码是否重复
        RoleDetailVO roleByEnname = roleMapper.getExistByEnname(operatorRoleDto.getEnname(), operatorRoleDto.getRoleId());
        if (roleByEnname != null) {
            throw new BusinessException(ErrorEnum.CODE_EXIST);
        }
        UserVo user = UserUtils.getUser();
        Long userId = user.getUserId();
        //如果查询结果为空，执行新增，反之，执行修改
        if (StringUtils.isBlank(operatorRoleDto.getRoleId())) {

            roleDetailVO.setRoleId(UUIDUtils.uuid());
            roleDetailVO.setCreateBy(operatorRoleDto.getUpdateBy());
            roleDetailVO.setCreateDate(new Date());
            roleDetailVO.setUpdateDate(new Date());
            roleDetailVO.setUpdateBy(String.valueOf(userId));
            roleDetailVO.setCreateBy(String.valueOf(userId));
            if (StringUtils.isBlank(roleDetailVO.getDataScope())) {
                //默认数据范围
                roleDetailVO.setDataScope("1");
            }
            RoleEntity roleEntity = new RoleEntity();

            dozerBeanMapper.map(roleDetailVO, roleEntity);
            if (StringUtils.isEmpty(roleEntity.getDelFlag())) {
                roleEntity.setDelFlag("0");
            }
            log.info("roleEntity 参数={}", roleEntity.toString());
            roleMapper.insert(roleEntity);
            if (operatorRoleDto.getMenuIds() != null && operatorRoleDto.getMenuIds().size() > 0) {
                roleMenuMapper.insert(operatorRoleDto.getMenuIds(), roleDetailVO.getRoleId());
            }
            return roleDetailVO;
        } else {
            roleDetailVO.setRoleId(roleDetailVO.getRoleId());
            roleDetailVO.setUpdateDate(new Date());
            roleDetailVO.setUpdateBy(String.valueOf(userId));
            List<RoleMenuEntity> byRoleId = roleMenuMapper.getByRoleId(operatorRoleDto.getRoleId());
            if (CollectionUtils.isEmpty(byRoleId)) {
                if (operatorRoleDto.getMenuIds() != null && operatorRoleDto.getMenuIds().size() > 0) {
                    roleMenuMapper.insert(operatorRoleDto.getMenuIds(), operatorRoleDto.getRoleId());
                }
            } else {
                // 更新关联表
                roleMenuMapper.deleteMenuFromRole(operatorRoleDto.getRoleId());
                if (operatorRoleDto.getMenuIds() != null && operatorRoleDto.getMenuIds().size() > 0) {
                    log.info("Ids={},Id={}", operatorRoleDto.getMenuIds(), operatorRoleDto.getRoleId());
                    roleMenuMapper.insert(operatorRoleDto.getMenuIds(), operatorRoleDto.getRoleId());
                }
            }
            // 更新角色表
            roleMapper.update(roleDetailVO);
            return roleDetailVO;
        }
    }

    @Override
    public RoleDetailVO getRoleByEnname(String enname) {
        RoleDetailVO roleDetailVO = roleMapper.getRoleByEnname(enname);
        return roleDetailVO;
    }

    @Override
    public RoleDetailVO getRoleByName(String name) {
        RoleDetailVO roleByName = roleMapper.getByName(name, null);
        return roleByName;
    }

    @Override
    public Page<RoleDetailVO> searchRole(Page<RoleDetailVO> page, SearchByConditionDTO searchByConditionDTO) {

        if (StringUtils.isBlank(searchByConditionDTO.getName())) {
            List<RoleDetailVO> roleDetailVOS = roleMapper.listRole(page, null);

            List<RoleDetailVO> roleDetailVOS1 = roleDetailVOS.stream().map(roleDetailVO -> {
                List<RoleMenuEntity> roleMenuEntities = roleMenuMapper.getByRoleId(roleDetailVO.getRoleId());
                List<String> menuIds = roleMenuEntities.stream().map(roleMenuEntity -> roleMenuEntity.getMenuId()).collect(Collectors.toList());
                roleDetailVO.setMenuIds(menuIds);
                return roleDetailVO;
            }).collect(Collectors.toList());
            page.setRecords(roleDetailVOS1);
            return page;
        }

        List<RoleDetailVO> roleDetailVOS = roleMapper.searchRole(page, searchByConditionDTO.getName());

        List<RoleDetailVO> roleDetailVOS1 = roleDetailVOS.stream().map(roleDetailVO -> {
            List<RoleMenuEntity> roleMenuEntities = roleMenuMapper.getByRoleId(roleDetailVO.getRoleId());
            List<String> menuIds = roleMenuEntities.stream().map(roleMenuEntity -> roleMenuEntity.getMenuId()).collect(Collectors.toList());
            roleDetailVO.setMenuIds(menuIds);
            return roleDetailVO;
        }).collect(Collectors.toList());
        page.setRecords(roleDetailVOS1);
        return page;
    }

    @Override
    public Integer assignRole(AssignRoleDTO assignRoleDTO) {

        if (assignRoleDTO == null || StringUtils.isBlank(assignRoleDTO.getRoleId()) || CollectionUtils.isEmpty(assignRoleDTO.getUserIds())) {
            throw new BusinessException(ErrorEnum.PARAMS_WRONG);
        }
        List<Long> userIds = assignRoleDTO.getUserIds();
        List<Long> existUserIds = roleUserMapper.selectExistUserOfRole(userIds, assignRoleDTO.getRoleId());

        //去除已经存在得用户
        if (existUserIds != null && existUserIds.size() > 0) {
            userIds.removeAll(existUserIds);
        }
        assignRoleDTO.setUserIds(userIds);
        roleUserMapper.insert(assignRoleDTO);
        return userIds.size();
    }

    @Override
    public Integer deleteUserFromRole(DeleteUserFromRoleDTO deleteUserFromRoleDTO) {

        RoleUserEntity roleUserEntity = roleUserMapper.selectByRoleIdAndUserId(deleteUserFromRoleDTO.getUserId(), deleteUserFromRoleDTO.getRoleId());
        if (roleUserEntity == null) {
            throw new BusinessException(ErrorEnum.PARAMS_WRONG);
        }
        return roleUserMapper.deleteUserFromRole(deleteUserFromRoleDTO.getUserId(), deleteUserFromRoleDTO.getRoleId());
    }

    @Override
    public Page<AssignedUserVO> listUserAssignedRole(ListAssignedUserDTO listAssignedUserDTO) {

        Page<AssignedUserVO> page = new Page<>(listAssignedUserDTO.getPageNum(), listAssignedUserDTO.getPageSize());

        List<AssignedUserVO> assignedUserVOS = userMapper.pageUser(page, listAssignedUserDTO.getRoleId());
        page.setRecords(assignedUserVOS);
        return page;
    }
}
