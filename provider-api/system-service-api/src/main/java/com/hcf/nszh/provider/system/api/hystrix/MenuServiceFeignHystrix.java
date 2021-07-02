package com.hcf.nszh.provider.system.api.hystrix;

import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.provider.system.api.service.MenuApiService;
import com.hcf.nszh.provider.system.api.dto.MenuDto;
import com.hcf.nszh.provider.system.api.dto.OperatorMenuDto;
import com.hcf.nszh.provider.system.api.dto.SearchByConditionDTO;
import com.hcf.nszh.provider.system.api.dto.UpdateMenuSortDto;
import com.hcf.nszh.provider.system.api.vo.MenuVo;
import com.hcf.nszh.provider.system.api.vo.OperatorMenuVo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author hx
 * @Date 2019/7/11
 **/
@Component
public class MenuServiceFeignHystrix implements MenuApiService {
    @Override
    public ResponseVo deleteMenuById(String menuId) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<MenuVo> getMenuById(MenuDto menuDto) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<List<MenuVo>> listMenu() {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<OperatorMenuVo> operatorMenu(OperatorMenuDto operatorMenuDto) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<List<OperatorMenuVo>> updateSort(UpdateMenuSortDto updateMenuSortDto) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<List<MenuVo>> searchMenu(SearchByConditionDTO searchByConditionDTO) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }

    @Override
    public ResponseVo<List<MenuVo>> getMenuByName(String name, String parentId) {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }
}
