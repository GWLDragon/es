package com.hcf.nszh.provider.system.api.service;

import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.provider.system.api.config.SystemFeignConfiguration;
import com.hcf.nszh.provider.system.api.hystrix.AreaServiceFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author hcf
 */
@FeignClient(value = "system-service", path = "/area",
        configuration = SystemFeignConfiguration.class,
        fallback = AreaServiceFeignHystrix.class)
@Service
public interface AreaApiService {

    @GetMapping(value = "/list")
    ResponseVo list();

}
