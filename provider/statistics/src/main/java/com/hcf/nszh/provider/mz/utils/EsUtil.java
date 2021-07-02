package com.hcf.nszh.provider.mz.utils;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author gwl
 * @date 2021/6/25 16:25
 * @since 1.0.0
 */

public class EsUtil {
    /**
     *  指定注入的bean
     */
    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient restHighLevelClient;


    /**
     * 创建索引，重复创建会抛出相关异常
     * */
    public void contextLoadTest() throws IOException {
        CreateIndexRequest my_index = new CreateIndexRequest("my_index");
        System.out.println(restHighLevelClient);
        CreateIndexResponse indexResponse = restHighLevelClient.indices().create(my_index, RequestOptions.DEFAULT);
        System.out.println(indexResponse.toString());
    }

    /**
     * 查询索引是否存在
     */
    public  void existRequest() throws IOException {
        GetIndexRequest my_index = new GetIndexRequest("my_index");
        System.out.println(my_index.toString());
        boolean exists = restHighLevelClient.indices().exists(my_index, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     *
     * 删除索引，索引不存在会抛出相关异常
     * */
    public void deleteRequest() throws IOException {
        DeleteIndexRequest my_index = new DeleteIndexRequest("my_index");
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(my_index, RequestOptions.DEFAULT);
        System.out.println(delete.toString());
    }
}
