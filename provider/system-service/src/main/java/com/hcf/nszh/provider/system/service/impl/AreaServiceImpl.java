package com.hcf.nszh.provider.system.service.impl;

import com.hcf.nszh.provider.system.mapper.AreaMapper;
import com.hcf.nszh.provider.system.service.AreaService;
import com.hcf.nszh.provider.system.entity.AreaEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author  Furant 2019/7/4 23:50
 */

@Service
@Slf4j
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaMapper areaMapper;

    @Override
    public List<AreaEntity> list() {
        return areaMapper.findAllList();
    }
}
