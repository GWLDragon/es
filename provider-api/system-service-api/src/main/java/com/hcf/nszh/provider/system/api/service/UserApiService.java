package com.hcf.nszh.provider.system.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.provider.system.api.config.SystemFeignConfiguration;
import com.hcf.nszh.provider.system.api.dto.*;
import com.hcf.nszh.provider.system.api.hystrix.UserServiceFeignHystrix;
import com.hcf.nszh.provider.system.api.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author huangxiong
 * @Date 2019/6/29
 **/
@FeignClient(value = "system-service", path = "/user",
        configuration = SystemFeignConfiguration.class,
        fallback = UserServiceFeignHystrix.class)
@Service
public interface UserApiService {

    /**
     * 根据登录名，获得用户信息
     *
     * @param loginName
     * @return
     */
    @GetMapping("/getUserVoByLoginName")
    ResponseVo<UserVo> getUserVoByLoginName(@RequestParam(value = "loginName") String loginName);

    /**
     * 更新用户登录信息
     *
     * @param loginName
     * @param request
     * @return
     */
    @PostMapping("/updateUserLoginInfo")
    ResponseVo<String> updateUserLoginInfo(@RequestParam(value = "loginName") String loginName,
                                           HttpServletRequest request);

    /**
     * 保存用户
     *
     * @param saveUserDto
     * @return
     */
    @PostMapping("/saveUser")
    ResponseVo<String> saveUser(@Valid @RequestBody SaveUserDto saveUserDto);

    @GetMapping("/findRoledUser")
    ResponseVo<List<UserVo>> findRoledUser(@RequestParam(value = "roleId") String roleId,
                                           @RequestParam(value = "officeId", required = false) String officeId);

    @PostMapping(value = "/list")
    ResponseVo<Page<UserPageVo>> list(@RequestBody QueryUserPageDto queryUserPageDto);

    @GetMapping(value = "/getUserId/{userId}")
    ResponseVo<UserVo> getUserId(@PathVariable("userId") Long userId);

    @GetMapping(value = "/getUserByUserId/{userId}")
    ResponseVo<UserVo> getUserByUserId(@PathVariable("userId") Long userId);

    @GetMapping(value = "/unRoledUser/{roleId}")
    ResponseVo<List<UserBaseVo>> unRoledUser(@PathVariable("roleId") String roleId);

    @GetMapping(value = "/unRoledOfficeOfUser/{roleId}")
    ResponseVo<List<OfficeOfUserVo>> unRoledOfficeOfUser(@PathVariable("roleId") String roleId);

    @GetMapping(value = "/findUserByRoleId/{roleId}")
    ResponseVo<Map<String, Object>> findUserByRoleId(@RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                                     @RequestParam(required = false, defaultValue = "10", value = "pageSize") int pageSize,
                                                     @PathVariable("roleId") String roleId);

    @GetMapping(value = "/getUserByName/{userName}")
    ResponseVo<UserInfoVo> getUserByName(@PathVariable("userName") String userName, HttpServletRequest request);

    @PostMapping("/distributeUser")
    ResponseVo<String> distributeUser(@RequestBody DistributeDto distributeDto);

    @GetMapping(value = "/infoData")
    ResponseVo<UserVo> infoData();

    @PostMapping("/modifyPwd")
    ResponseVo<UserVo> modifyPwd(@RequestBody UpdatePasswordDTO users);

    /**
     * 查询没有小组的用户列表
     */
    @PostMapping("/userInfoList")
    ResponseVo<List<UserPageVo>> listUserExceptTeam(@RequestBody List<Long> userIds);


    @GetMapping("searchByName")
    ResponseVo<List<UserPageVo>> searchUserByName(@RequestParam("userName") String userName);

    @PostMapping("deleteUser")
    ResponseVo<String> deleteUser(@RequestBody DeleteUserDto deleteUserDto);

    @GetMapping("listAllUser")
    ResponseVo<List<UserVo>> listAllUser();

    @GetMapping("getMyOfficeUser")
    ResponseVo<List<UserBaseVo>> getMyOfficeUser(@RequestParam(required = false, value = "roleCode") String roleCode);

    @GetMapping("getAllOfficeAuditUser")
    ResponseVo<List<OfficeOfUserVo>> getAllOfficeAuditUser(@RequestParam(required = false, value = "roleCode") String roleCode);

    @GetMapping("getMyOfficeRootId")
    ResponseVo<String> getMyOfficeRootId();

    @GetMapping(value = "/getUserByLoginName")
    ResponseVo<UserVo> getUserByLoginName(@RequestParam(value = "loginName") String loginName);


    @GetMapping(value = "saveSkin")
    ResponseVo<Boolean> seveUserSkin(@RequestParam(value = "userId") String userId, @RequestParam(value = "skin") String skin, @RequestParam(value = "sysSkin") String sysSkin);

}
