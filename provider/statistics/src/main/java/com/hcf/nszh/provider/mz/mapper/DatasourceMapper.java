package com.hcf.nszh.provider.mz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hcf.nszh.provider.mz.entity.Datasource;
import com.hcf.nszh.provider.mz.utils.redis.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 数据源配置表 Mapper 接口
 * </p>
 *
 * @author LC
 * @since 2020-06-22
 */
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
public interface DatasourceMapper extends BaseMapper<Datasource> {

}
