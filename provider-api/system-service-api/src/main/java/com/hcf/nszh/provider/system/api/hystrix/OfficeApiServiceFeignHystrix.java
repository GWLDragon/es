package com.hcf.nszh.provider.system.api.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.provider.system.api.service.OfficeApiService;
import com.hcf.nszh.provider.system.api.dto.OfficeIdDTO;
import com.hcf.nszh.provider.system.api.dto.OperatorOfficeDTO;
import com.hcf.nszh.provider.system.api.dto.SearchOfficeDTO;
import com.hcf.nszh.provider.system.api.vo.AddressBookVo;
import com.hcf.nszh.provider.system.api.vo.OfficeDetailVO;
import com.hcf.nszh.provider.system.api.vo.OfficeVo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @Author huangxiong
 * @Date 2019/7/22
 **/
@Component
public class OfficeApiServiceFeignHystrix implements OfficeApiService {

    @Override
    public ResponseVo<List<OfficeVo>> listOffice() {
        return new ResponseVo<>(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<OfficeVo> getOffice(OfficeIdDTO officeIdDTO) {
        return new ResponseVo<>(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<OfficeDetailVO> operatorOffice(OperatorOfficeDTO operatorOfficeDTO) {
        return new ResponseVo<>(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<String> deleteOfficeById(OfficeIdDTO officeIdDTO) {
        return new ResponseVo<>(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<List<Map<String, Object>>> treeData(String extId, String type, Long grade, Boolean isAll) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<List<OfficeVo>> getUserOfficeTree() {
        return new ResponseVo<>(ErrorEnum.USER_SERVICE_ERROR);
    }


    @Override
    @PostMapping("/searchOffice")
    public ResponseVo<List<OfficeVo>> searchOffice(@RequestBody SearchOfficeDTO searchOfficeDTO) {
        return new ResponseVo<>(ErrorEnum.USER_SERVICE_ERROR);

    }

    @Override
    public ResponseVo<Page<AddressBookVo>> addressBook(int pageNum, int pageSize, String search, List<String> officeIds) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<List<OfficeVo>> getRootOffice() {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<OfficeVo> getRootOfficeById(String officeId) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<String> getRootOfficeIdByUserId(Long userId) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<List<OfficeVo>> getOfficeByName(String name, String parentId) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<List<OfficeVo>> getOfficeByCode(String code, String parentId) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }
}
