package com.hcf.nszh.provider.mz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.provider.mz.entity.TestEntity;
import com.hcf.nszh.provider.mz.enums.DBTypes;
import com.hcf.nszh.provider.mz.mapper.EsMapper;
import com.hcf.nszh.provider.mz.service.EsService;
import com.hcf.nszh.provider.mz.utils.NameUtil;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author gwl
 * @date 2021/6/25 16:47
 * @since 1.0.0
 */
@Service
@DS(DBTypes.MYSQL)
public class EsServiceImpl implements EsService {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private EsMapper esMapper;

    /**
     * 添加doc,当指定id的数据存在时，则会变成全量update，却字段会导致数据丢失
     */
    @Override
    public void addDocTest() throws IOException {
        //创建请求连接
        IndexRequest request = new IndexRequest("my_index");
        //创建要添加的信息
        TestEntity user = new TestEntity("孙悟空", 700);
        //通过客户端对基本信息进行添加
        //自定义id
        request.id("1");
        //请求超时时间
        request.timeout("10s");
        //将请求的数据放到request中，并指定消息类型为json
        request.source(JSON.toJSONString(user), XContentType.JSON);
        //通过客户端传递请求
        IndexResponse index = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(index.toString());
        System.out.println(index.status());
    }


    /**
     * 修改doc，（上面的创建其实是可以智能的在有数据的情况下是更新数据，但是是全量的更新，缺字段会导致数据丢失）
     */
    @Override
    public void updateDoc() throws IOException {
        //修改指定id的doc数据，此时如果id不存在会抛出未找到的异常
        UpdateRequest updateRequest = new UpdateRequest("my_index", "1");
        updateRequest.timeout("5s");
        TestEntity user = new TestEntity("猪八戒", 300);
        updateRequest.doc(JSON.toJSONString(user), XContentType.JSON);
        //获取修改之后的结果
        updateRequest.fetchSource(true);
        UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(update.toString());
        System.out.println(update.getIndex());
        System.out.println(update.getGetResult());
        System.out.println(update.status());

    }

    /**
     * 删除doc
     */
    @Override
    public void deleteDoc() throws IOException {
        //创建删除请求，指定索引和删除id
        DeleteRequest deleteRequest = new DeleteRequest("my_index", "1");
        DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(delete.status());
        System.out.println(delete.toString());

    }

    @Override
    public List<TestEntity> queryTest(Integer pageNum, Integer pageSize) {
        Page<TestEntity> page = new Page<>(pageNum, pageSize);
        List<TestEntity> testEntities = esMapper.queryTest(page);
        return testEntities;
    }

    @Override
    @Async
    public void insertToTest() {
        for (int i = 0; i < 100000000; i++) {
            if (0 <= i && i < 85000000) {
                esMapper.insertTest(NameUtil.randomName(true, 3));
            } else if (85000000 <= i && i < 90000000) {
                esMapper.insertTest(NameUtil.randomName(false, 3));
            } else if (90000000 <= i && i < 95000000) {
                esMapper.insertTest(NameUtil.randomName(true, 4));
            } else {
                esMapper.insertTest(NameUtil.randomName(false, 4));
            }

        }
    }

    @Override
    public List<Map<String, Object>> queryTest2(Integer pageNum, Integer pageSize) {
        Page<TestEntity> page = new Page<>(pageNum, pageSize);
        return esMapper.queryTest2(page);
    }
}
