package com.hcf.nszh.provider.mz.controller;


import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.provider.mz.config.ElasticSearchUtils;
import com.hcf.nszh.provider.mz.entity.TestEntity;
import com.hcf.nszh.provider.mz.service.EsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author gwl
 * @date 2021/6/28 11:29
 * @since 1.0.0
 */
@Api(tags = "ElasticSearch控制器")
@RestController
@RequestMapping("/elasticSearch")
public class ElasticSearchController {

    @Autowired
    private ElasticSearchUtils elasticSearchUtils;

    @Autowired
    private EsService esService;

    /**
     * 新增索引
     *
     * @param index 索引
     * @return ResponseVo
     */
    @ApiOperation("新增索引")
    @PostMapping("/createIndex")
    public ResponseVo<?> createIndex(String index) throws IOException {
        return new ResponseVo(ErrorEnum.SUCCESS, elasticSearchUtils.createIndex(index));
    }

    /**
     * 索引是否存在
     *
     * @param index index
     * @return ResponseVo
     */
    @ApiOperation("索引是否存在")
    @GetMapping("/existIndex/{index}")
    public ResponseVo<?> existIndex(@PathVariable String index) throws IOException {
        return new ResponseVo(ErrorEnum.SUCCESS, elasticSearchUtils.isIndexExist(index));
    }

    /**
     * 删除索引
     *
     * @param index index
     * @return ResponseVo
     */
    @ApiOperation("删除索引")
    @DeleteMapping("/deleteIndex/{index}")
    public ResponseVo<?> deleteIndex(@PathVariable String index) throws IOException {
        return new ResponseVo(ErrorEnum.SUCCESS, elasticSearchUtils.deleteIndex(index));
    }


    /**
     * 新增/更新数据
     *
     * @param entity 数据
     * @param index  索引
     * @param esId   esId
     * @return ResponseVo
     */
    @ApiOperation("新增/更新数据")
    @PostMapping("/submitData")
    public ResponseVo<String> submitData(TestEntity entity, String index, String esId) throws IOException {
        return new ResponseVo(ErrorEnum.SUCCESS, elasticSearchUtils.submitData(entity, index, esId));
    }

    /**
     * 新增/更新数据
     *
     * @param index 索引
     * @return ResponseVo
     */
    @ApiOperation("新增批量数据")
    @PostMapping("/testDataList")
    public ResponseVo<?> testDataList(String index) throws IOException {
        elasticSearchUtils.testDataList(index);
        return new ResponseVo(ErrorEnum.SUCCESS);
    }

    /**
     * 通过id删除数据
     *
     * @param index index
     * @param id    id
     * @return ResponseVo
     */
    @ApiOperation("通过id删除数据")
    @DeleteMapping("/deleteDataById/{index}/{id}")
    public ResponseVo<String> deleteDataById(@PathVariable String index, @PathVariable String id) throws IOException {
        return new ResponseVo(ErrorEnum.SUCCESS, elasticSearchUtils.deleteDataById(index, id));
    }

    /**
     * 通过id查询数据
     *
     * @param index  index
     * @param id     id
     * @param fields 需要显示的字段，逗号分隔（缺省为全部字段）
     * @return ResponseVo
     */
    @ApiOperation("通过id查询数据")
    @GetMapping("/searchDataById")
    public ResponseVo<Map<String, Object>> searchDataById(String index, String id, String fields) throws IOException {
        return new ResponseVo(ErrorEnum.SUCCESS, elasticSearchUtils.searchDataById(index, id, fields));
    }

    /**
     * 分页查询（这只是一个demo）
     *
     * @param index index
     * @return ResponseVo
     */
    @ApiOperation("分页查询")
    @GetMapping("/selectPage")
    public ResponseVo<?> selectPage(String index) throws IOException {
        //构建查询条件
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //精确查询
        //boolQueryBuilder.must(QueryBuilders.wildcardQuery("name", "张三"));
        // 模糊查询
        boolQueryBuilder.filter(QueryBuilders.wildcardQuery("name", "张"));
        // 范围查询 from:相当于闭区间; gt:相当于开区间(>) gte:相当于闭区间 (>=) lt:开区间(<) lte:闭区间 (<=)
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("age").from(18).to(32));
        SearchSourceBuilder query = new SearchSourceBuilder();
        query.query(boolQueryBuilder);
        //需要查询的字段，缺省则查询全部
        String fields = "";
        //需要高亮显示的字段
        String highlightField = "name";
        if (StringUtils.isNotBlank(fields)) {
            //只查询特定字段。如果需要查询所有字段则不设置该项。
            query.fetchSource(new FetchSourceContext(true, fields.split(","), Strings.EMPTY_ARRAY));
        }
        //分页参数，相当于pageNum
        Integer from = 0;
        //分页参数，相当于pageSize
        Integer size = 2;
        //设置分页参数
        query.from(from);
        query.size(size);

        //设置排序字段和排序方式，注意：字段是text类型需要拼接.keyword
//        query.sort("age", SortOrder.DESC);
        query.sort("name" + ".keyword", SortOrder.ASC);

        return new ResponseVo(ErrorEnum.SUCCESS, elasticSearchUtils.searchListData(index, query, highlightField));
    }

    @ApiOperation("添加")
    @GetMapping("/test2")
    public ResponseVo<?> test2() throws Exception {
        for (int i = 1; i <4 ; i++) {
            List<Map<String, Object>> mapList = esService.queryTest2(i, 5000);
            elasticSearchUtils.test2(mapList);
        }

        return new ResponseVo(ErrorEnum.SUCCESS);
    }
}
