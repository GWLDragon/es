package com.hcf.nszh.provider.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.common.annotation.RequestLogging;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.common.utils.StringUtils;
import com.hcf.nszh.provider.system.api.dto.*;
import com.hcf.nszh.provider.system.api.vo.*;
import com.hcf.nszh.provider.system.entity.UserEntity;
import com.hcf.nszh.provider.system.service.MenuService;
import com.hcf.nszh.provider.system.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author hx
 * @Date 2019/7/1
 **/

@Slf4j
@RequestMapping(value = "user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @RequestLogging
    @GetMapping("/getUserVoByLoginName")
    @ExceptionHandler(Exception.class)
    public ResponseVo<UserVo> getUserVoByLoginName(@ApiParam(value = "用户名", required = true)
                                                   @RequestParam(value = "loginName")
                                                           String loginName) {
        return new ResponseVo(ErrorEnum.SUCCESS, userService.getUserVoByLoginName(loginName));
    }

    @RequestLogging
    @PostMapping("/updateUserLoginInfo")
    @ExceptionHandler(Exception.class)
    public ResponseVo<String> updateUserLoginInfo(@ApiParam(value = "用户名", required = true)
                                                  @RequestParam(value = "loginName")
                                                          String loginName,
                                                  HttpServletRequest request) {
        return new ResponseVo(ErrorEnum.SUCCESS, userService.updateUserLoginInfo(loginName,
                StringUtils.getRemoteAddr(request)));
    }

    @RequestLogging
    @PostMapping("/saveUser")
    @ExceptionHandler(Exception.class)
    public ResponseVo<String> saveUser(@Valid @RequestBody SaveUserDto saveUserDto) {
        return new ResponseVo(ErrorEnum.SUCCESS, userService.saveOrUpdateUser(saveUserDto));
    }

    @RequestLogging
    @GetMapping("/findRoledUser")
    @ExceptionHandler(Exception.class)
    public ResponseVo<List<UserVo>> findRoledUser(@RequestParam(value = "roleId") String roleId,
                                                  @RequestParam(value = "officeId") String officeId) {
        return new ResponseVo(ErrorEnum.SUCCESS, userService.findRoledUser(roleId, officeId));
    }

    @RequestLogging
    @PostMapping(value = "/list")
    @ExceptionHandler(Exception.class)
    ResponseVo<Page<UserPageVo>> list(@RequestBody QueryUserPageDto queryUserPageDto) {
        return new ResponseVo(ErrorEnum.SUCCESS, userService.list(queryUserPageDto));
    }

    @RequestLogging
    @GetMapping(value = "/getUserId/{userId}")
    @ExceptionHandler(Exception.class)
    ResponseVo<UserVo> getUserId(@PathVariable Long userId) {
        return new ResponseVo(ErrorEnum.SUCCESS, userService.getUserId(userId));
    }

    @RequestLogging
    @GetMapping(value = "/getUserByUserId/{userId}")
    @ExceptionHandler(Exception.class)
    ResponseVo<UserVo> getUserByUserId(@PathVariable Long userId) {
        return new ResponseVo(ErrorEnum.SUCCESS, userService.getUserByUserId(userId));
    }

    @GetMapping(value = "/unRoledUser/{roleId}")
    @ExceptionHandler(Exception.class)
    ResponseVo<List<UserPageVo>> unRoledUser(@PathVariable String roleId) {
        return new ResponseVo(ErrorEnum.SUCCESS, userService.unRoledUser(roleId));
    }

    @GetMapping(value = "/unRoledOfficeOfUser/{roleId}")
    @ExceptionHandler(Exception.class)
    ResponseVo<List<UserPageVo>> unRoledOfficeOfUser(@PathVariable String roleId) {
        return new ResponseVo(ErrorEnum.SUCCESS, userService.unRoledOfficeOfUser(roleId));
    }


    @GetMapping(value = "/findUserByRoleId/{roleId}")
    @ExceptionHandler(Exception.class)
    ResponseVo<Map<String, Object>> findUserByRoleId(@RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                                     @RequestParam(required = false, defaultValue = "10", value = "pageSize") int pageSize,
                                                     @PathVariable String roleId) {
        return new ResponseVo(ErrorEnum.SUCCESS, userService.findUserByRoleId(roleId, pageNumber, pageSize));
    }

    @RequestLogging
    @GetMapping(value = "/getUserByName/{userName}")
    @ExceptionHandler(Exception.class)
    ResponseVo<UserInfoVo> getUserByName(@PathVariable String userName, HttpServletRequest request) {
        UserVo userVo = userService.getUserVoByLoginName(userName);
        List<MenuVo> menu = menuService.listMenu();
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setMenu(menu);
        userInfoVo.setUser(userVo);
        userInfoVo.setRoot(menuService.getRootMenu());
        return new ResponseVo(ErrorEnum.SUCCESS, userInfoVo);
    }

    @PostMapping("/distributeUser")
    ResponseVo<String> distributeUser(@RequestBody DistributeDto distributeDto) {
        return null;
    }

    @RequestLogging
    @GetMapping(value = "/infoData")
    @ExceptionHandler(Exception.class)
    ResponseVo<UserVo> infoData() {
        UserVo userVo = userService.infoData();
        log.info("返回=============结果{}", userVo);
        return new ResponseVo(ErrorEnum.SUCCESS, userVo);
    }

    @RequestLogging
    @PostMapping("/modifyPwd")
    @ExceptionHandler(Exception.class)
    ResponseVo<UserVo> modifyPwd(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        return new ResponseVo(ErrorEnum.SUCCESS, userService.modifyPassword(updatePasswordDTO));
    }

    @RequestLogging
    @PostMapping("/userInfoList")
    @ApiOperation("查询不在小组列表中的用户成员")
    @ExceptionHandler(Exception.class)
    public ResponseVo<List<UserPageVo>> listUserExceptTeam(@RequestBody List<Long> userIds) {
        List<UserPageVo> userPageVos = userService.listUserExceptTeam(userIds);
        return new ResponseVo<>(ErrorEnum.SUCCESS, userPageVos);
    }

    @RequestLogging
    @PostMapping("/modifyPassword")
    @ExceptionHandler(Exception.class)
    public ResponseVo<UserVo> modifyPassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        return new ResponseVo(ErrorEnum.SUCCESS, userService.modifyPassword(updatePasswordDTO));
    }

    @GetMapping("searchByName")
    @ApiOperation("根据名称模糊查询，在分配角色等地方用")
    @ExceptionHandler(Exception.class)
    public ResponseVo<List<UserPageVo>> searchUserByName(@Param("userName") String userName) {
        return new ResponseVo<>(ErrorEnum.SUCCESS, userService.searchUserByName(userName));
    }

    @RequestLogging
    @PostMapping("deleteUser")
    @ExceptionHandler(Exception.class)
    public ResponseVo<String> deleteUser(@RequestBody DeleteUserDto deleteUserDto) {
        return new ResponseVo<>(ErrorEnum.SUCCESS, userService.deleteUser(deleteUserDto.getUserId()));
    }

    @RequestLogging
    @GetMapping("listAllUser")
//    @ExceptionHandler(Exception.class)
    public ResponseVo<List<UserVo>> listAllUser() {
        return new ResponseVo(ErrorEnum.SUCCESS, userService.listAllUser());
    }

    @GetMapping("getMyOfficeUser")
    @ExceptionHandler(Exception.class)
    public ResponseVo<List<UserBaseVo>> getMyOfficeUser(@RequestParam(required = false, value = "roleCode") String roleCode) {
        return new ResponseVo(ErrorEnum.SUCCESS, userService.getMyOfficeUser(roleCode));
    }

    @GetMapping("getAllOfficeAuditUser")
    @ExceptionHandler(Exception.class)
    public ResponseVo<List<OfficeOfUserVo>> getAllOfficeAuditUser(@RequestParam(required = false, value = "roleCode") String roleCode) {
        return new ResponseVo(ErrorEnum.SUCCESS, userService.getAllOfficeAuditUser(roleCode));
    }

    @GetMapping("getMyOfficeRootId")
    @ExceptionHandler(Exception.class)
    public ResponseVo<String> getMyOfficeRootId() {
        return new ResponseVo(ErrorEnum.SUCCESS, userService.getMyOfficeRootId());
    }

    @RequestLogging
    @GetMapping(value = "/getUserByLoginName")
    @ExceptionHandler(Exception.class)
    public ResponseVo<UserVo> getUserByLoginName(@RequestParam(value = "loginName") String loginName) {
        UserEntity userByLoginName = userService.getUserByLoginName(loginName);
        return new ResponseVo(ErrorEnum.SUCCESS, userByLoginName);
    }


    @GetMapping(value = "/saveSkin")
    @ExceptionHandler(Exception.class)
    public ResponseVo<Boolean> seveUserSkin(@RequestParam(required = true, value = "userId") String userId,
                                            @RequestParam(required = false, value = "skin") String skin,
                                            @RequestParam(required = false, value = "sysSkin") String sysSkin) {
        Boolean aBoolean = null;
        try {
            aBoolean = userService.saveSkin(userId, skin, sysSkin);
        } catch (Exception e) {
            log.error("保存皮肤错误==={}", e);
        }
        return new ResponseVo<>(ErrorEnum.SUCCESS, aBoolean);

    }


}
