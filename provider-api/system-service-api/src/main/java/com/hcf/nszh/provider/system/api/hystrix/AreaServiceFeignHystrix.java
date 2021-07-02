package com.hcf.nszh.provider.system.api.hystrix;

import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.provider.system.api.service.AreaApiService;
import org.springframework.stereotype.Component;

/**
 * @Author lc
 * @Date 2020/9/28
 **/
@Component
public class AreaServiceFeignHystrix implements AreaApiService {
    @Override
    public ResponseVo list() {
        return new ResponseVo(ErrorEnum.USER_SERVICE_ERROR);
    }
}
