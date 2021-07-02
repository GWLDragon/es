package com.hcf.nszh.provider.mz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.provider.mz.vo.FileInfoVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author gwl
 * @date 2021/5/27 15:51
 * @since 1.0.0
 */

public interface NsfwService {

    /**
     * 导入数据
     *
     * @param file
     * @return
     */
    List<String> importData(MultipartFile file);

    /**
     * 服务渠道
     *
     * @return
     */
    ResponseVo<List<Map<String, Object>>> fwqd();

    /**
     * 服务趋势
     *
     * @return
     */
    ResponseVo<List<Map<String, Object>>> fwqs();

    /**
     * 热点问题
     *
     * @return
     */
    ResponseVo<List<Map<String, Object>>> rdwt();

    /**
     * 咨询热词
     *
     * @return
     */
    ResponseVo<List<Map<String, Object>>> zxrc();

    /**
     * 知识类别
     *
     * @return
     */
    ResponseVo<List<Map<String, Object>>> zslb();

    /**
     * 知识总量
     *
     * @return
     */
    ResponseVo<List<Map<String, Object>>> zszl();

    /**
     * 知识类型
     *
     * @return
     */
    ResponseVo<List<Map<String, Object>>> zslx();

    /**
     * 合计
     *
     * @return
     */
    ResponseVo<List<Map<String, Object>>> hj();

    /**
     * 热点问题（数据展示平台)
     *
     * @return
     */
    ResponseVo<List<Map<String, Object>>> sjptRdwt();


    /**
     * 数据维护
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<FileInfoVO> fileInfoQueryPage(Integer pageNum, Integer pageSize);

    /**
     * 12366智能客服
     *
     * @return
     */
    ResponseVo<List<Map<String, Object>>> znkf();

    /**
     * 服务渠道智能
     *
     * @return
     */
    ResponseVo<Long> fwqdzn();

}
