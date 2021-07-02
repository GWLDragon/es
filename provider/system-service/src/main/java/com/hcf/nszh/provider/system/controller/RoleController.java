package com.hcf.nszh.provider.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.common.annotation.RequestLogging;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.provider.system.api.dto.*;
import com.hcf.nszh.provider.system.api.vo.AssignedUserVO;
import com.hcf.nszh.provider.system.api.vo.RoleDetailVO;
import com.hcf.nszh.provider.system.api.vo.RoleVo;
import com.hcf.nszh.provider.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Furant
 * 2019/7/3 22:53
 */
@Slf4j
@RestController
@RequestMapping("/role")
@Api(value = "RoleController")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestLogging
    @GetMapping(value = "/deleteRoleByRoleId")
    @ExceptionHandler(Exception.class)
    public ResponseVo deleteRoleByRoleId(@RequestParam(value = "roleId") String roleId) {
        return new ResponseVo(ErrorEnum.SUCCESS, roleService.deleteRoleByRoleId(roleId));
    }

    @RequestLogging
    @RequestMapping(value = "/getRoleByRoleId", method = RequestMethod.GET)
    @ExceptionHandler(Exception.class)
    public ResponseVo<RoleVo> getRoleByRoleId(@RequestParam("roleId") String roleId) {
        return new ResponseVo(ErrorEnum.SUCCESS, roleService.getRoleByRoleId(roleId));
    }

    @RequestLogging
    @RequestMapping(value = "/listRole", method = RequestMethod.GET)
    @ExceptionHandler(Exception.class)
    public ResponseVo<List<RoleDetailVO>> listRole() {
        return new ResponseVo(ErrorEnum.SUCCESS, roleService.listRoles());
    }

    @RequestLogging
    @RequestMapping(value = "/listPageRoles", method = RequestMethod.GET)
    @ExceptionHandler(Exception.class)
    public ResponseVo<Page<RoleDetailVO>> listPageRoles(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        @RequestParam(required = false,  value = "search") String search) {

        Page<RoleDetailVO> page = new Page<>(pageNum, pageSize);
        page = roleService.listPageRoles(page,search);
        return new ResponseVo(ErrorEnum.SUCCESS, page);
    }

    @RequestLogging
    @RequestMapping(value = "/operatorRole", method = RequestMethod.POST)
    @ExceptionHandler(Exception.class)
    public ResponseVo<RoleDetailVO> operatorRole(@Valid @RequestBody OperatorRoleDto operatorRoleDto) {
        RoleDetailVO roleDetailVO = null;
        try {
            roleDetailVO = roleService.operatorRole(operatorRoleDto);
            log.info("返回角色添加=========-----============结果{}", roleDetailVO);
        }catch (Exception e){
            log.info("返回角色添加=========-----=============异常{}", e);
        }
        return new ResponseVo<>(ErrorEnum.SUCCESS, roleDetailVO);
    }

    @RequestLogging
    @GetMapping(value = "/getRoleByEnname")
    @ExceptionHandler(Exception.class)
    public ResponseVo<RoleVo> getRoleByEnname(@ApiParam(value = "用户名", required = true)
                                              @RequestParam(value = "enname")
                                                      String enname) {
        return new ResponseVo(ErrorEnum.SUCCESS, roleService.getRoleByEnname(enname));
    }

    @RequestLogging
    @GetMapping(value = "/getRoleByName")
    @ExceptionHandler(Exception.class)
    public ResponseVo<RoleVo> getRoleByName(@ApiParam(value = "用户名", required = true)
                                              @RequestParam(value = "name")
                                                      String name) {
        return new ResponseVo(ErrorEnum.SUCCESS, roleService.getRoleByName(name));
    }

    @RequestLogging
    @PostMapping("/searchRole")
    @ExceptionHandler(Exception.class)
    public ResponseVo<Page<RoleDetailVO>> searchRole(@RequestBody SearchByConditionDTO searchByConditionDTO) {

        Page<RoleDetailVO> page = new Page<>(searchByConditionDTO.getPageNum(), searchByConditionDTO.getPageSize());
        page = roleService.searchRole(page, searchByConditionDTO);
        return new ResponseVo<>(ErrorEnum.SUCCESS, page);
    }

    @RequestLogging
    @PostMapping("/assignRole")
    @ExceptionHandler(Exception.class)
    public ResponseVo<Integer> assignRole(@RequestBody AssignRoleDTO assignRoleDTO) {
        Integer integer = null;
        try {
            integer = roleService.assignRole(assignRoleDTO);
        }catch (Exception e){
            log.error("修改用户角色====异常={}",e);
        }
        return new ResponseVo<>(ErrorEnum.SUCCESS,integer );
    }

    @RequestLogging
    @PostMapping("deleteUserFromRole")
    @ExceptionHandler(Exception.class)
    public ResponseVo<Integer> deleteUserFromRole(@RequestBody DeleteUserFromRoleDTO deleteUserFromRoleDTO) {
        return new ResponseVo(ErrorEnum.SUCCESS, roleService.deleteUserFromRole(deleteUserFromRoleDTO));
    }

    @RequestLogging
    @PostMapping("getUserRoleList")
    @ApiOperation("获取已经分配过角色的用户信息")
    @ExceptionHandler(Exception.class)
    public ResponseVo<Page<AssignedUserVO>> getUserRoleList(@RequestBody ListAssignedUserDTO listAssignedUserDTO) {
        return new ResponseVo(ErrorEnum.SUCCESS, roleService.listUserAssignedRole(listAssignedUserDTO));
    }

}
