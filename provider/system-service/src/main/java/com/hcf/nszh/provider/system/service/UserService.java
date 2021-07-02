package com.hcf.nszh.provider.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.provider.system.api.dto.QueryUserPageDto;
import com.hcf.nszh.provider.system.api.dto.SaveUserDto;
import com.hcf.nszh.provider.system.api.dto.UpdatePasswordDTO;
import com.hcf.nszh.provider.system.api.vo.OfficeOfUserVo;
import com.hcf.nszh.provider.system.api.vo.UserBaseVo;
import com.hcf.nszh.provider.system.api.vo.UserPageVo;
import com.hcf.nszh.provider.system.api.vo.UserVo;
import com.hcf.nszh.provider.system.entity.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户服务接口
 */
public interface UserService {
    /**
     * 根据登录名，获得用户信息
     *
     * @param loginName
     * @return
     */
    UserVo getUserVoByLoginName(String loginName);

    /**
     * 更新本次登录信息
     *
     * @param loginName
     * @param ip
     * @return
     */
    String updateUserLoginInfo(String loginName, String ip);

    /**
     * 保存用户
     *
     * @param saveUserDto
     * @return
     */
    String saveOrUpdateUser(SaveUserDto saveUserDto);

    /**
     * 根据角色和机构查用户
     *
     * @param roleId
     * @param officeId
     * @return
     */
    List<UserVo> findRoledUser(String roleId, String officeId);

    /**
     * 查询用户列表，分页
     *
     * @param queryUserPageDto
     * @return
     */
    Page<UserPageVo> list(QueryUserPageDto queryUserPageDto);

    /**
     * 根据userId，查询用户
     *存缓存
     * @param userId
     * @return
     */
    UserVo getUserId(Long userId);

    /**
     * 根据userId，查询用户
     *
     * @param userId
     * @return
     */
    UserVo getUserByUserId(Long userId);



    /**
     * 查询登录用户信息
     *
     * @return
     */
    UserVo infoData();

    /**
     * 查询没有小组的用户列表
     */
    List<UserPageVo> listUserExceptTeam(List<Long> userIds);

    UserVo modifyPassword(UpdatePasswordDTO updatePasswordDTO);


   /* List<UserPageVo> listUser();*/

    List<UserPageVo> searchUserByName(String userName);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    String deleteUser(Long userId);


    List<UserVo> listAllUser();

    /**
     * 根据角色ID查询用户
     *
     * @param roleId
     * @return
     */
    Map<String,Object> findUserByRoleId(String roleId, int pageNumber, int pageSize);

    List<UserBaseVo> unRoledUser(String roleId);

    List<OfficeOfUserVo> unRoledOfficeOfUser(String roleId);

    /**
     * 获取本部门人员
     * @param roleCode
     * @return
     */
    List<UserBaseVo> getMyOfficeUser(String roleCode);

    /**
     * 获取所有机构的审核人员
     * @param roleCode
     * @return
     */
    List<OfficeOfUserVo> getAllOfficeAuditUser(String roleCode);

    /**
     * 获取操作者的根机构ID
     * @return
     */
    String getMyOfficeRootId();

    UserEntity getUserByLoginName(String loginName);


    Boolean saveSkin(String userId,String skin ,String sysSkin);



}
