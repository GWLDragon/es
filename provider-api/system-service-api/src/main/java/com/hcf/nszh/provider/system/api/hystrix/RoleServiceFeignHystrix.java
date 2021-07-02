package com.hcf.nszh.provider.system.api.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.provider.system.api.dto.*;
import com.hcf.nszh.provider.system.api.service.RoleApiService;
import com.hcf.nszh.provider.system.api.vo.AssignedUserVO;
import com.hcf.nszh.provider.system.api.vo.RoleDetailVO;
import com.hcf.nszh.provider.system.api.vo.RoleVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author huangxiong
 * @Date 2019/6/29
 **/
@Component
public class RoleServiceFeignHystrix implements RoleApiService {


    @Override
    public ResponseVo<RoleVo> getRoleByEnname(String enname) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<RoleVo> getRoleByName(String name) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo deleteRoleByRoleId(String roleId) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<RoleVo> getRoleByRoleId(String roleId) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<List<RoleDetailVO>> listRole() {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<Page<RoleDetailVO>> listPageRoles(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        @RequestParam(required = false,  value = "search") String search) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<RoleDetailVO> operatorRole(@RequestBody OperatorRoleDto operatorRoleDto) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<Page<RoleDetailVO>> searchRole(@RequestBody SearchByConditionDTO searchByConditionDTO) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    @PostMapping("assignRole")
    public ResponseVo<Integer> assignRole(@RequestBody AssignRoleDTO assignRoleDTO) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    @PostMapping("deleteUserFromRole")
    public ResponseVo<Integer> deleteUserFromRole(@RequestBody DeleteUserFromRoleDTO deleteUserFromRoleDTO) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    @PostMapping("getUserRoleList")
    @ApiOperation("获取已经分配过角色的用户信息")
    public ResponseVo<Page<AssignedUserVO>> getUserRoleList(@RequestBody ListAssignedUserDTO listAssignedUserDTO) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }
}
