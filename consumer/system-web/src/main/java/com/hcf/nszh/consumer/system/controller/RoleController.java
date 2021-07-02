package com.hcf.nszh.consumer.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.common.annotation.RequestLogging;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.provider.system.api.dto.*;
import com.hcf.nszh.provider.system.api.vo.AssignedUserVO;
import com.hcf.nszh.provider.system.api.vo.RoleDetailVO;
import com.hcf.nszh.provider.system.api.vo.RoleVo;
import com.hcf.nszh.provider.system.api.service.RoleApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author maruko
 * 2019/7/3 22:53
 */
@Api(value = "API - RoleController", description = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleApiService roleApiService;

    @ApiOperation("根据角色ID删除角色，use")
    @RequestLogging
    @GetMapping(value = "/deleteRoleByRoleId")
    public ResponseVo deleteRoleByRoleId(@RequestParam(value = "roleId") String roleId) {
        return new ResponseVo(ErrorEnum.SUCCESS, roleApiService.deleteRoleByRoleId(roleId));
    }

    @ApiOperation("根据角色ID获取角色信息，use")
    @RequestLogging
    @RequestMapping(value = "/getRoleByRoleId", method = RequestMethod.GET)
    public ResponseVo<RoleVo> getRoleByRoleId(@RequestParam("roleId") String roleId) {
        ResponseVo<RoleVo> roleByRoleId = roleApiService.getRoleByRoleId(roleId);
        return roleByRoleId;
    }

    @ApiOperation("角色列表，use")
    @RequestLogging
    @RequestMapping(value = "/listRole", method = RequestMethod.GET)
    public ResponseVo<List<RoleDetailVO>> listRole() {
        ResponseVo<List<RoleDetailVO>> listResponseVo = roleApiService.listRole();
        return listResponseVo;
    }

    @ApiOperation("角色列表带分页，use")
    @RequestLogging
    @RequestMapping(value = "/listPageRoles", method = RequestMethod.GET)
    public ResponseVo<Page<RoleDetailVO>> listPageRoles(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum,
                                                        @RequestParam(required = false, defaultValue = "10", value = "pageSize") int pageSize,
                                                        @RequestParam(required = false, value = "search") String search) {
        ResponseVo<Page<RoleDetailVO>> pageResponseVo = roleApiService.listPageRoles(pageNum, pageSize, search);
        return pageResponseVo;
    }

    @ApiOperation("新增/修改 角色，use")
    @RequestLogging
    @RequestMapping(value = "/operatorRole", method = RequestMethod.POST)
    public ResponseVo<RoleDetailVO> operatorRole(@Valid @RequestBody OperatorRoleDto operatorRoleDto) {
        ResponseVo<RoleDetailVO> responseVo = roleApiService.operatorRole(operatorRoleDto);
        return responseVo;
    }

    @ApiOperation("编码查询,use")
    @RequestLogging
    @GetMapping(value = "/getRoleByEnName")
    public ResponseVo<RoleVo> getRoleByEnName(@ApiParam(value = "编码", required = true)
                                              @RequestParam(value = "enName")
                                                      String enName) {

        ResponseVo<RoleVo> roleByEnName = roleApiService.getRoleByEnname(enName);
        return roleByEnName;
    }

    @ApiOperation("名称查询,use")
    @RequestLogging
    @GetMapping(value = "/getRoleByName")
    public ResponseVo<RoleVo> getRoleByName(@ApiParam(value = "名称", required = true)
                                            @RequestParam(value = "name")
                                                    String name) {
        ResponseVo<RoleVo> roleByName = roleApiService.getRoleByName(name);
        return roleByName;
    }


    @RequestLogging
    @PostMapping("/searchRole")
    public ResponseVo<Page<RoleDetailVO>> searchRole(@RequestBody SearchByConditionDTO searchByConditionDTO) {
        ResponseVo<Page<RoleDetailVO>> pageResponseVo = roleApiService.searchRole(searchByConditionDTO);
        return pageResponseVo;
    }

    @ApiOperation("给用户分配角色,use")
    @RequestLogging
    @PostMapping("/assignRole")
    public ResponseVo<Integer> assignRole(@RequestBody AssignRoleDTO assignRoleDTO) {

        ResponseVo<Integer> integerResponseVo = roleApiService.assignRole(assignRoleDTO);
        return integerResponseVo;
    }

    @ApiOperation("根据用户ID和角色ID移除角色下的用户，use")
    @RequestLogging
    @PostMapping("deleteUserFromRole")
    public ResponseVo<Integer> deleteUserFromRole(@RequestBody DeleteUserFromRoleDTO deleteUserFromRoleDTO) {
        return roleApiService.deleteUserFromRole(deleteUserFromRoleDTO);
    }

    @RequestLogging
    @PostMapping("getUserRoleList")
    @ApiOperation("获取已经分配过角色的用户信息")
    public ResponseVo<Page<AssignedUserVO>> getUserRoleList(@RequestBody ListAssignedUserDTO listAssignedUserDTO) {
        return roleApiService.getUserRoleList(listAssignedUserDTO);
    }

}
