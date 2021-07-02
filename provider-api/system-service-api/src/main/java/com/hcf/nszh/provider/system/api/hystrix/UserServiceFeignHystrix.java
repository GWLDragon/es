package com.hcf.nszh.provider.system.api.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.provider.system.api.dto.*;
import com.hcf.nszh.provider.system.api.service.UserApiService;
import com.hcf.nszh.provider.system.api.vo.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author huangxiong
 * @Date 2019/6/29
 **/
@Component
public class UserServiceFeignHystrix implements UserApiService {

    @Override
    public ResponseVo<UserVo> getUserVoByLoginName(String loginName) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<String> updateUserLoginInfo(String loginName, HttpServletRequest request) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<String> saveUser(@Valid SaveUserDto saveUserDto) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<List<UserVo>> findRoledUser(String roleId, String officeId) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<Page<UserPageVo>> list(QueryUserPageDto queryUserPageDto) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }


    @Override
    public ResponseVo<UserVo> getUserId(Long userId) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<UserVo> getUserByUserId(Long userId) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<List<UserBaseVo>> unRoledUser(String roleId) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<List<OfficeOfUserVo>> unRoledOfficeOfUser(String roleId) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<Map<String,Object>> findUserByRoleId(int pageNumber, int pageSize, String roleId) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<UserInfoVo> getUserByName(String userName, HttpServletRequest request) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<String> distributeUser(DistributeDto distributeDto) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<UserVo> infoData() {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }


    @Override
    public ResponseVo<UserVo> modifyPwd(UpdatePasswordDTO users) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    /**
     * 查询没有小组的用户列表
     */
    @Override
    @PostMapping("/userInfoList")
    public ResponseVo<List<UserPageVo>> listUserExceptTeam(@RequestBody List<Long> userIds) {
        return new ResponseVo<>(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<List<UserPageVo>> searchUserByName(String userName) {
        return new ResponseVo<>(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<String> deleteUser(DeleteUserDto deleteUserDto) {
        return new ResponseVo<>(ErrorEnum.USER_SERVICE_ERROR);
    }


    @Override
    public ResponseVo<List<UserVo>> listAllUser(){
        return new ResponseVo<>(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<List<UserBaseVo>> getMyOfficeUser(String roleCode) {
        return new ResponseVo<>(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<List<OfficeOfUserVo>> getAllOfficeAuditUser(String roleCode) {
        return new ResponseVo<>(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<String> getMyOfficeRootId() {
        return new ResponseVo<>(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<UserVo> getUserByLoginName(String loginName) {
        return new ResponseVo<>(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<Boolean> seveUserSkin(String userId, String skin ,String sysSkin) {
        return new ResponseVo<>(ErrorEnum.USER_SERVICE_ERROR);
    }
}
