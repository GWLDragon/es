package com.hcf.nszh.provider.mz.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.provider.mz.vo.*;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author gwl
 * @date 2021/6/7 11:21
 * @since 1.0.0
 */
@CacheNamespace(flushInterval = 60000)
@Repository
public interface NsfwMapper {

    int truncateTableData(@Param("type") int type);

    int createTable(@Param("type") int type);

    int addZsData(@Param("vos") List<ZsVO> vos);

    int addRdData(@Param("vos") List<RdVO> vos);

    int addFwqkData(@Param("vos") List<FwqkVO> vos);

    int addZszlData(@Param("vos") List<ZszlVO> vos);

    int addRdwtData(@Param("vos") List<RdwtVO> vos);

    /**
     * 服务渠道
     *
     * @return
     */
    List<Map<String, Object>> fwqd();

    /**
     * 服务趋势
     *
     * @return
     */
    List<Map<String, Object>> fwqs();

    /**
     * 热点问题
     *
     * @return
     */
    List<Map<String, Object>> rdwt();

    /**
     * 咨询热词
     *
     * @return
     */
    List<Map<String, Object>> zxrc();

    /**
     * 知识类别
     *
     * @return
     */
    List<Map<String, Object>> zslb();

    /**
     * 知识总量
     *
     * @return
     */
    List<Map<String, Object>> zszl();

    /**
     * 知识类型
     *
     * @return
     */
    List<Map<String, Object>> zslx();

    /**
     * 合计
     *
     * @return
     */
    List<Map<String, Object>> hj();

    /**
     * 热点问题（数据展示平台)
     *
     * @return
     */
    List<Map<String, Object>> sjptRdwt();

    int addFileInfo(@Param("vo") FileInfoVO vo);

    List<FileInfoVO> queryFileInfo(Page<FileInfoVO> page);

    /**
     * 热点问题（数据展示平台)
     *
     * @return
     */
    List<Map<String, Object>> znkf();

    /**
     * 服务渠道智能
     *
     * @return
     */
    Long fwqdzn();
}
