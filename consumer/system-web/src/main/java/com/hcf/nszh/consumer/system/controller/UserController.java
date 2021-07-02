package com.hcf.nszh.consumer.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.common.annotation.RequestLogging;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.common.enums.NumberEnum;
import com.hcf.nszh.common.utils.AesUtils;
import com.hcf.nszh.common.utils.PassValidationWordUtils;
import com.hcf.nszh.provider.system.api.dto.DeleteUserDto;
import com.hcf.nszh.provider.system.api.dto.QueryUserPageDto;
import com.hcf.nszh.provider.system.api.dto.SaveUserDto;
import com.hcf.nszh.provider.system.api.dto.UpdatePasswordDTO;
import com.hcf.nszh.provider.system.api.service.UserApiService;
import com.hcf.nszh.provider.system.api.vo.*;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author maruko
 * 2019/7/18 19:39
 */
@Api(value = "API - UserController", description = "用户管理")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserApiService userApiService;

    @GetMapping("searchByName")
    @ApiOperation("根据名称模糊查询，在分配角色等地方用")
    public ResponseVo<List<UserPageVo>> searchUserByName(@Param("userName") String userName) {
        ResponseVo<List<UserPageVo>> listResponseVo = userApiService.searchUserByName(userName);
        return listResponseVo;
    }

    @RequestLogging
    @ApiOperation(value = "添加或修改用户,use", notes = "添加或修改用户")
    @PostMapping("/saveUser")
    public ResponseVo<String> saveUser(@Valid @RequestBody SaveUserDto saveUserDto) {
        String pwIdent = saveUserDto.getPassword();
        if (Strings.isNotBlank(pwIdent)) {
            String passwordAes = AesUtils.aesDecrypt(pwIdent);
            if (passwordAes.length() > NumberEnum.THIRTY) {
                return new ResponseVo(-2, "用户密码不能超出30个字符");
            }

            boolean isTruePassword = PassValidationWordUtils.isTruePassword(passwordAes);
            if (!isTruePassword) {
                return new ResponseVo(-2, "用户密码至少是八位数，同时包含大小写字母、数字、及特殊字符");
            }
            boolean isSeqString = PassValidationWordUtils.isSeqString(passwordAes);
            if (isSeqString) {
                return new ResponseVo(-2, "用户密码不允许使用连续3位以上的字符（或键盘上的连续字符）");
            }
            boolean continuousKB = PassValidationWordUtils.continuouskb(passwordAes);
            if (continuousKB) {
                return new ResponseVo(-2, "用户密码不允许使用连续3位以上的字符（或键盘上的连续字符）");
            }

        }

        return userApiService.saveUser(saveUserDto);
    }

    @RequestLogging
    @GetMapping("/findRoleUser")
    public ResponseVo<List<UserVo>> findRoleUser(@RequestParam(value = "roleId") String roleId,
                                                 @RequestParam(value = "officeId") String officeId) {
        return userApiService.findRoledUser(roleId, officeId);
    }

    @RequestLogging
    @ApiOperation(value = "获取用户列表,use", notes = "获取用户列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNumber", value = "页号", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", paramType = "query", defaultValue = "10")})
    @PostMapping(value = "/list")
    ResponseVo<Page<UserPageVo>> list(@RequestBody QueryUserPageDto queryUserPageDto) {
        return userApiService.list(queryUserPageDto);
    }

    @RequestLogging
    @ApiOperation(value = "获取单个的用户信息", notes = "获取单个的用户信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "用户id", paramType = "path")})
    @GetMapping(value = "/getUserId/{userId}")
    ResponseVo<UserVo> getUserId(@PathVariable Long userId) {
        return userApiService.getUserId(userId);
    }

    @RequestLogging
    @ApiOperation(value = "根据用户ID获取用户信息，编辑用户的时候用到，use", notes = "获取单个的用户信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "用户id", paramType = "path")})
    @GetMapping(value = "/getUserByUserId/{userId}")
    ResponseVo<UserVo> getUserByUserId(@PathVariable Long userId) {
        return userApiService.getUserId(userId);
    }

    @ApiOperation(value = "获取没有分配该角色的用户，use", notes = "获取角色的用户信息")
    @GetMapping(value = "/unRoleUser/{roleId}")
    ResponseVo<List<UserBaseVo>> unRoledUser(@PathVariable String roleId) {
        return userApiService.unRoledUser(roleId);
    }

    @ApiOperation(value = "获取没有分配该角色的机构下的用户，use", notes = "获取角色的用户信息")
    @GetMapping(value = "/unRoleOfficeOfUser/{roleId}")
    ResponseVo<List<OfficeOfUserVo>> unRoledOfficeOfUser(@PathVariable String roleId) {
        return userApiService.unRoledOfficeOfUser(roleId);
    }

    @ApiOperation(value = "根据角色ID查询角色下的用户，use", notes = "获取角色的用户信息")
    @GetMapping(value = "/findUserByRoleId/{roleId}")
    ResponseVo<Map<String, Object>> findUserByRoleId(@RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                                     @RequestParam(required = false, defaultValue = "10", value = "pageSize") int pageSize,
                                                     @PathVariable String roleId) {
        return userApiService.findUserByRoleId(pageNumber, pageSize, roleId);
    }

    @RequestLogging
    @ApiOperation(value = "获取单个的用户信息", notes = "获取单个的用户信息")
    @GetMapping(value = "/getUserByName/{userName}")
    ResponseVo<UserInfoVo> getUserByName(@PathVariable String userName, HttpServletRequest request) {
        return userApiService.getUserByName(userName, request);
    }


    @RequestLogging
    @ApiOperation(value = "获取当前用户信息,use", notes = "获取当前用户信息")
    @GetMapping(value = "/infoData")
    ResponseVo<UserVo> infoData() {
        ResponseVo<UserVo> userVoResponseVo = userApiService.infoData();
        log.info("用户端==========返回结果={}", userVoResponseVo);
        return userVoResponseVo;
    }

    @RequestLogging
    @ApiOperation(value = "修改个人用户密码", notes = "修改个人用户密码")
    @PostMapping("/modifyPwd")
    ResponseVo<UserVo> modifyPwd(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        return userApiService.modifyPwd(updatePasswordDTO);
    }

    @ApiOperation(value = "删除用户，use", notes = "删除用户")
    @RequestLogging
    @PostMapping("deleteUser")
    public ResponseVo<String> deleteUser(@RequestBody DeleteUserDto deleteUserDto) {
        return userApiService.deleteUser(deleteUserDto);
    }

    @RequestLogging
    @GetMapping("listAllUser")
    public ResponseVo<List<UserVo>> listAllUser() {
        return userApiService.listAllUser();
    }

    /**
     * 获取本机构的用户
     *
     * @param roleCode 角色code，如果角色code为空则查询本机构的所有用户
     * @return
     */
    @ApiOperation(value = "获取本机构的用户,如果角色code为空则查询本机构的所有用户", notes = "获取本机构的用户")
    @GetMapping("getMyOfficeUser")
    public ResponseVo<List<UserBaseVo>> getMyOfficeUser(@RequestParam(required = false, value = "roleCode") String roleCode) {
        return new ResponseVo(ErrorEnum.SUCCESS, userApiService.getMyOfficeUser(roleCode));
    }

    /**
     * 获取所有机构的用户,
     *
     * @param roleCode
     * @return
     */
    @ApiOperation(value = "获取所有机构的用户,如果角色code为空则查询机构的所有用户", notes = "获取所有机构的用户")
    @GetMapping("getAllOfficeAuditUser")
    public ResponseVo<List<OfficeOfUserVo>> getAllOfficeAuditUser(@RequestParam(required = false, value = "roleCode") String roleCode) {
        return new ResponseVo(ErrorEnum.SUCCESS, userApiService.getAllOfficeAuditUser(roleCode));
    }

    @ApiOperation("用户名查询,use")
    @RequestLogging
    @GetMapping(value = "/getLoginName")
    public ResponseVo<UserVo> getUserByLoginName(@ApiParam(value = "用户名", required = true)
                                                 @RequestParam(value = "loginName")
                                                         String loginName) {
        ResponseVo<UserVo> roleByName = userApiService.getUserByLoginName(loginName);
        return roleByName;
    }

    @ApiOperation(value = "添加用户皮肤", notes = "保存用户皮肤")
    @GetMapping(value = "/saveSkin")
    public ResponseVo<?> saveUserSkin(@RequestParam(value = "userId") String userId,
                                      @RequestParam(required = false, value = "skin") String skin,
                                      @RequestParam(required = false, value = "sysSkin") String sysSkin) {
        ResponseVo<Boolean> booleanResponseVo = userApiService.seveUserSkin(userId, skin, sysSkin);
        return new ResponseVo<>(ErrorEnum.SUCCESS, booleanResponseVo);

    }
}
