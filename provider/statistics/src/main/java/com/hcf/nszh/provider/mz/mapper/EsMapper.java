package com.hcf.nszh.provider.mz.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.provider.mz.entity.TestEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author gwl
 * @date 2021/6/28 14:17
 * @since 1.0.0
 */
@Repository
public interface EsMapper {

    @Select("select * from test")
    List<TestEntity> queryTest(@Param("page") Page<TestEntity> page);

    @Insert("insert into test values(#{name},rand()*100)")
    void insertTest(@Param("name") String name);

    @Select("select * from test")
    List<Map<String,Object>> queryTest2(@Param("page") Page<TestEntity> page);
}
