package com.hcf.nszh.provider.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.provider.system.api.dto.GetOfficeTreeDTO;
import com.hcf.nszh.provider.system.api.dto.OfficeIdDTO;
import com.hcf.nszh.provider.system.api.dto.OperatorOfficeDTO;
import com.hcf.nszh.provider.system.api.dto.SearchOfficeDTO;
import com.hcf.nszh.provider.system.api.vo.AddressBookVo;
import com.hcf.nszh.provider.system.api.vo.OfficeDetailVO;
import com.hcf.nszh.provider.system.api.vo.OfficeVo;

import java.util.List;

/**
 * Created by Furant
 * 2019/7/22 10:29
 */
public interface OfficeService {

    List<OfficeVo> listOffice();

    OfficeVo getOfficeById(OfficeIdDTO officeIdDTO);

    String deleteOfficeById(OfficeIdDTO officeIdDTO);

    OfficeDetailVO operatorOffice(OperatorOfficeDTO operatorOfficeDTO);

    List<OfficeVo> getCurrentUserOfficeTree();

    List<OfficeVo> searchOffice(SearchOfficeDTO searchOfficeDTO);

    Page<AddressBookVo> addressBook(int pageNum, int pageSize, String search, List<String> officeIds);

    List<OfficeVo> getRootOffice();

    OfficeVo getRootOfficeById(String officeId);

    String getRootOfficeIdByUserId(Long userId);

    List<OfficeVo> getOfficeByName(String name,String parentId);

    List<OfficeVo> getOfficeByCode(String code,String parentId);

}
