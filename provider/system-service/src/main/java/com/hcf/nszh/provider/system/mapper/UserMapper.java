package com.hcf.nszh.provider.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.common.constant.SystemConstant;
import com.hcf.nszh.provider.system.api.dto.QueryUserPageDto;
import com.hcf.nszh.provider.system.api.dto.SaveUserDto;
import com.hcf.nszh.provider.system.api.vo.AssignedUserVO;
import com.hcf.nszh.provider.system.api.vo.UserBaseVo;
import com.hcf.nszh.provider.system.api.vo.UserByRoleVo;
import com.hcf.nszh.provider.system.api.vo.UserPageVo;
import com.hcf.nszh.provider.system.entity.UserEntity;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@CacheNamespace(readWrite = true, flushInterval = 60000)
@Repository
public interface UserMapper extends BaseMapper<UserEntity> {
    /**
     * 根据登录名称查询用户
     *
     * @param loginName
     * @return
     */
    UserEntity getByLoginName(@Param(value = "loginName") String loginName);

    List<UserEntity> selectRoleUser(@Param("roleId") String roleId,
                                    @Param("officeId") String officeId);

    List<UserPageVo> queryUserPage(Page<UserPageVo> page, @Param("queryUserPageDto") QueryUserPageDto queryUserPageDto);

    /**
     * 根据用户ID，查询用户
     *
     * @param userId
     * @return
     */
    @Select("select * from sys_user where user_id = #{userId} and ROWNUM<=1")
    UserEntity getUserById(@Param("userId") Long userId);

    /**
     * 查询不在小组中的用户的信息
     *
     * @return
     */
    List<UserPageVo> listUser(@Param("userIds") List<Long> userIds);

    /**
     * 修改密码
     *
     * @param userEntity
     * @return
     */
    int updatePasswordById(UserEntity userEntity);


    /**
     * 删除用户角色关联数据
     *
     * @param userId
     * @return
     */
    int deleteUserRole(@Param("userId") String userId);

    /**
     * 插入用户角色关联数据
     *
     * @param saveUserDto
     * @return
     */
    int insertUserRole(SaveUserDto saveUserDto);

    List<UserEntity> selectByIds(@Param("userIds") List<Long> userIds);

    List<UserEntity> getUserByNameLike(@Param("userName") String userName);

    @Update("update sys_user set del_flag = "+ SystemConstant.DEL_FLAG_DELETE + " where user_id = #{1}")
    int updateUserDelFlagByUserId(Long userId);


    List<UserEntity> listAllUser();

    List<UserPageVo> getUserPageVOByUserIds(@Param("userIds") List<Long> userIds);

    Long updateUserById(UserEntity userEntity);

    List<UserEntity> listUserByOfficeId(@Param("officeId") String officeId);

    List<UserPageVo> listUserRole(Page<UserPageVo> page,@Param("userIds") List<Long> userIds);


    List<AssignedUserVO> pageUser(Page<AssignedUserVO> page, @Param("roleId") String roleId);

    /**
     * 根据用户名查询用户表是否存在重名的有效用户
     * @param loginName
     * @return
     */
    UserEntity getUserByLoginName(@Param(value = "loginName") String loginName,@Param(value = "userId") Long userId );

    int updateByPrimaryKeySelective(UserEntity userEntity);

    List <UserByRoleVo> selectUserByRole(Page<UserByRoleVo> page, @Param("roleId") String roleId, @Param("officeId") String officeId);

    List<UserBaseVo> selectUserOfNoRole(@Param("roleId") String roleId);

    List<UserEntity> getUserByOfficeIds(Page<UserEntity> page ,@Param("officeIds") List<String> officeIds, @Param("search") String search);

    /**
     * 添加用户皮肤
     */
    Boolean saveSkin(@Param("userId") String user, @Param("skin") String skin ,@Param("sysSkin") String sysSkin);
}
