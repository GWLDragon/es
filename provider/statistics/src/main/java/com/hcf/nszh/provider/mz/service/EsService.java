package com.hcf.nszh.provider.mz.service;

import com.hcf.nszh.provider.mz.entity.TestEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author gwl
 * @date 2021/6/25 16:46
 * @since 1.0.0
 */

public interface EsService {

    void addDocTest() throws IOException;

    void updateDoc() throws IOException;

    void deleteDoc() throws IOException;

    List<TestEntity> queryTest(Integer pageNum,Integer pageSize);

    void insertToTest();

    List<Map<String,Object>> queryTest2(Integer pageNum,Integer pageSize);
}
