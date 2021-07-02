package com.hcf.nszh.provider.mz.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.common.security.shiro.utils.UserUtils;
import com.hcf.nszh.provider.mz.enums.TypeEnum;
import com.hcf.nszh.provider.mz.mapper.DatasourceMapper;
import com.hcf.nszh.provider.mz.mapper.NsfwMapper;
import com.hcf.nszh.provider.mz.service.MinioService;
import com.hcf.nszh.provider.mz.service.NsfwService;
import com.hcf.nszh.provider.mz.vo.*;
import com.hcf.nszh.provider.system.api.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
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
@Service
@Slf4j
public class NsfwServiceImpl implements NsfwService {

    @Autowired
    private NsfwMapper nsfwMapper;

    @Autowired
    private MinioService minioService;

    public static final int SHEETMIN = 1;

    public static final int SHEETMAX = 6;

    /**
     * 导入数据
     *
     * @param file
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> importData(MultipartFile file) {

        List<String> results = new ArrayList<>();
        try {

            InputStream inputStream = file.getInputStream();
            //处理流文件不能被多次读取问题
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > -1) {
                bs.write(buffer, 0, len);
            }
            bs.flush();


            for (int i = SHEETMIN; i < SHEETMAX; i++) {
                InputStream stream = new ByteArrayInputStream(bs.toByteArray());
                results.add(importDataInsert(i, stream));
                stream.close();
            }
        } catch (IOException o) {
            log.error("文件流异常");
        } finally {
            //上传文件获取路径
            String url = minioService.uploadFileMinio(file);
            //获取用户信息
            UserVo user = UserUtils.getUser();
            Long userId = user.getUserId();
            String userName = user.getName();
            //获取文件信息
            String filename = file.getOriginalFilename();
            //获取文件类型
            String type = filename.substring(filename.lastIndexOf(".") + 1);
            //获取文件大小
            Long size = file.getSize();
            //创建
            FileInfoVO vo = new FileInfoVO(filename.replace("." + type, ""), type, size, new Date(), userId, userName, url);
            nsfwMapper.addFileInfo(vo);

        }


        return results;
    }

    /**
     * 服务渠道
     *
     * @return
     */
    @Override
    public ResponseVo<List<Map<String, Object>>> fwqd() {
        return new ResponseVo<>(ErrorEnum.SUCCESS, nsfwMapper.fwqd());
    }

    /**
     * 服务趋势
     *
     * @return
     */
    @Override
    public ResponseVo<List<Map<String, Object>>> fwqs() {
        return new ResponseVo<>(ErrorEnum.SUCCESS, nsfwMapper.fwqs());
    }

    /**
     * 热点问题
     *
     * @return
     */
    @Override
    public ResponseVo<List<Map<String, Object>>> rdwt() {
        return new ResponseVo<>(ErrorEnum.SUCCESS, nsfwMapper.rdwt());
    }

    /**
     * 咨询热词
     *
     * @return
     */
    @Override
    public ResponseVo<List<Map<String, Object>>> zxrc() {
        return new ResponseVo<>(ErrorEnum.SUCCESS, nsfwMapper.zxrc());
    }

    /**
     * 知识类别
     *
     * @return
     */
    @Override
    public ResponseVo<List<Map<String, Object>>> zslb() {
        return new ResponseVo<>(ErrorEnum.SUCCESS, nsfwMapper.zslb());
    }

    /**
     * 知识总量
     *
     * @return
     */
    @Override
    public ResponseVo<List<Map<String, Object>>> zszl() {
        return new ResponseVo<>(ErrorEnum.SUCCESS, nsfwMapper.zszl());
    }

    /**
     * 知识类型
     *
     * @return
     */
    @Override
    public ResponseVo<List<Map<String, Object>>> zslx() {
        return new ResponseVo<>(ErrorEnum.SUCCESS, nsfwMapper.zslx());
    }

    /**
     * 合计
     *
     * @return
     */
    @Override
    public ResponseVo<List<Map<String, Object>>> hj() {
        return new ResponseVo<>(ErrorEnum.SUCCESS, nsfwMapper.hj());
    }

    /**
     * 热点问题（数据展示平台)
     *
     * @return
     */
    @Override
    public ResponseVo<List<Map<String, Object>>> sjptRdwt() {
        return new ResponseVo<>(ErrorEnum.SUCCESS, nsfwMapper.sjptRdwt());
    }

    @Override
    public Page<FileInfoVO> fileInfoQueryPage(Integer pageNum, Integer pageSize) {
        if (null == pageNum) {
            pageNum = 1;
        }
        if (null == pageSize) {
            pageSize = 10;
        }
        Page<FileInfoVO> page = new Page(pageNum, pageSize);
        List<FileInfoVO> fileInfoVOS = nsfwMapper.queryFileInfo(page);
        page.setRecords(fileInfoVOS);
        return page;
    }

    /**
     * 12366智能客服
     *
     * @return
     */
    @Override
    public ResponseVo<List<Map<String, Object>>> znkf() {
        return new ResponseVo<>(ErrorEnum.SUCCESS, nsfwMapper.znkf());
    }

    /**
     * 服务渠道智能
     *
     * @return
     */
    @Override
    public ResponseVo<Long> fwqdzn() {
        return new ResponseVo<>(ErrorEnum.SUCCESS, nsfwMapper.fwqdzn());
    }

    /**
     * 导入数据处理
     *
     * @param type
     * @param stream
     * @return
     */
    private String importDataInsert(int type, InputStream stream) {
        String s = null;
        switch (type) {
            //服务趋势（智能客服）数据读写
            case 1:
                List<FwqkVO> fwqkVOS = EasyExcel.read(stream)
                        .head(TypeEnum.getClassTypeByType(type))
                        // 设置sheet,默认读取第一个
                        .sheet(TypeEnum.getSheetByType(type))
                        // 设置标题所在行数
                        .headRowNumber(TypeEnum.getHeadRowNumberByType(type))
                        .doReadSync();
                try {
                    nsfwMapper.truncateTableData(type);
                } catch (RuntimeException r) {
                    nsfwMapper.createTable(type);
                } finally {
                    nsfwMapper.addFwqkData(fwqkVOS);
                }
                s = "服务趋势（智能客服）数据读写成功";
                break;
            //热点问题、咨询热词（智能客服）读写
            case 2:
                List<RdVO> rdVOS = EasyExcel.read(stream)
                        .head(TypeEnum.getClassTypeByType(type))
                        // 设置sheet,默认读取第一个
                        .sheet(TypeEnum.getSheetByType(type))
                        // 设置标题所在行数
                        .headRowNumber(TypeEnum.getHeadRowNumberByType(type))
                        .doReadSync();
                try {
                    nsfwMapper.truncateTableData(type);
                } catch (RuntimeException r) {
                    nsfwMapper.createTable(type);
                } finally {
                    nsfwMapper.addRdData(rdVOS);
                }
                s = "热点问题、咨询热词（智能客服）数据读写成功";
                break;
            //知识类别（智能客服）数据读写
            case 3:
                List<ZsVO> zsVOS = EasyExcel.read(stream)
                        .head(TypeEnum.getClassTypeByType(type))
                        // 设置sheet,默认读取第一个
                        .sheet(TypeEnum.getSheetByType(type))
                        // 设置标题所在行数
                        .headRowNumber(TypeEnum.getHeadRowNumberByType(type))
                        .doReadSync();
                try {
                    //清空表数据 需抓取表是否存在异常
                    nsfwMapper.truncateTableData(type);
                } catch (RuntimeException r) {
                    //表不存在创建表
                    nsfwMapper.createTable(type);
                } finally {
                    //插入数据
                    nsfwMapper.addZsData(zsVOS);
                }
                s = "知识类别（智能客服）数据读写成功";
                break;
            //知识总量数据读写
            case 4:
                List<ZszlVO> zszlVOS = EasyExcel.read(stream)
                        .head(TypeEnum.getClassTypeByType(type))
                        // 设置sheet,默认读取第一个
                        .sheet(TypeEnum.getSheetByType(type))
                        // 设置标题所在行数
                        .headRowNumber(TypeEnum.getHeadRowNumberByType(type))
                        .doReadSync();
                //处理数据 只取第一行（excel单元格问题）
                List listCopy = new ArrayList();
                listCopy.add(zszlVOS.get(0));
                zszlVOS = listCopy;
                try {
                    nsfwMapper.truncateTableData(type);
                } catch (RuntimeException e) {
                    nsfwMapper.createTable(type);
                } finally {
                    nsfwMapper.addZszlData(zszlVOS);
                }
                s = "知识总量数据读写成功";
                break;
            //热点问题（数据展示平台）数据读写
            case 5:
                List<RdwtVO> rdwtVOS = EasyExcel.read(stream)
                        .head(TypeEnum.getClassTypeByType(type))
                        // 设置sheet,默认读取第一个
                        .sheet(TypeEnum.getSheetByType(type))
                        // 设置标题所在行数
                        .headRowNumber(TypeEnum.getHeadRowNumberByType(type))
                        .doReadSync();
                //处理数据 只取第一行（excel单元格问题）
                try {
                    nsfwMapper.truncateTableData(type);
                } catch (RuntimeException e) {
                    nsfwMapper.createTable(type);
                } finally {
                    nsfwMapper.addRdwtData(rdwtVOS);
                }
                s = "热点问题（数据展示平台）数据读写成功";
                break;

        }

        if (Strings.isNullOrEmpty(s)) {
            s = TypeEnum.getMessageByType(type);
        }
        return s;
    }
}
