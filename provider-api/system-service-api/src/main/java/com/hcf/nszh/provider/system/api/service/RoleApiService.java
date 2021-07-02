package com.hcf.nszh.provider.system.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.provider.system.api.config.SystemFeignConfiguration;
import com.hcf.nszh.provider.system.api.dto.*;
import com.hcf.nszh.provider.system.api.hystrix.RoleServiceFeignHystrix;
import com.hcf.nszh.provider.system.api.vo.AssignedUserVO;
import com.hcf.nszh.provider.system.api.vo.RoleDetailVO;
import com.hcf.nszh.provider.system.api.vo.RoleVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * @author hcf
 */
@FeignClient(value = "system-service", path = "/role",
        configuration = SystemFeignConfiguration.class,
        fallback = RoleServiceFeignHystrix.class)
@Service
public interface RoleApiService {

    @GetMapping(value = "/getRoleByEnname")
    ResponseVo<RoleVo> getRoleByEnname(@RequestParam(value = "enname")
                                               String enname);

    @GetMapping(value = "/getRoleByName")
    ResponseVo<RoleVo> getRoleByName(@RequestParam(value = "name")
                                               String name);

    @GetMapping(value = "/deleteRoleByRoleId")
     ResponseVo deleteRoleByRoleId(@RequestParam(value = "roleId") String roleId);

    @GetMapping(value = "/getRoleByRoleId")
     ResponseVo<RoleVo> getRoleByRoleId(@RequestParam("roleId") String roleId);

    @GetMapping(value = "/listRole")
     ResponseVo<List<RoleDetailVO>> listRole();

    @GetMapping(value = "/listPageRoles")
     ResponseVo<Page<RoleDetailVO>> listPageRoles(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        @RequestParam(required = false,  value = "search") String search);

    @PostMapping(value = "/operatorRole")
     ResponseVo<RoleDetailVO> operatorRole(@Valid @RequestBody OperatorRoleDto operatorRoleDto);

    @PostMapping("/searchRole")
     ResponseVo<Page<RoleDetailVO>> searchRole(@RequestBody SearchByConditionDTO searchByConditionDTO);

    @PostMapping("assignRole")
     ResponseVo<Integer> assignRole(@RequestBody AssignRoleDTO assignRoleDTO);

    @PostMapping("deleteUserFromRole")
     ResponseVo<Integer> deleteUserFromRole(@RequestBody DeleteUserFromRoleDTO deleteUserFromRoleDTO);

    @PostMapping("getUserRoleList")
     ResponseVo<Page<AssignedUserVO>> getUserRoleList(@RequestBody ListAssignedUserDTO listAssignedUserDTO);
}
