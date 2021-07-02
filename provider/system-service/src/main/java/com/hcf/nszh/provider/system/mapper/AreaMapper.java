/**
 *  .
 */
package com.hcf.nszh.provider.system.mapper;


import com.hcf.nszh.provider.system.entity.AreaEntity;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hx
 * 区域DAO接口
 *  
 */
@CacheNamespace(flushInterval = 60000)
@Repository
public interface AreaMapper {

    /**
     * 获取单条数据
     * @param id
     * @return
     */
     AreaEntity get(String id);

    /**
     * 获取单条数据
     * @param entity
     * @return
     */
     AreaEntity get(AreaEntity entity);

    /**
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     * @param entity
     * @return
     */
     List<AreaEntity> findList(AreaEntity entity);

    /**
     * 查询所有数据列表
     * @param entity
     * @return
     */
     List<AreaEntity> findAllList(AreaEntity entity);

    /**
     * 查询所有数据列表
     * @see  List<AreaEntity> findAllList(T entity)
     * @return
     */
     List<AreaEntity> findAllList();

    /**
     * 插入数据
     * @param entity
     * @return
     */
     int insert(AreaEntity entity);

    /**
     * 更新数据
     * @param entity
     * @return
     */
     int update(AreaEntity entity);

    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     * @param id
     * @see  int delete(T entity)
     * @return
     */
    @Deprecated
     int delete(String id);

    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     * @param entity
     * @return
     */
     int delete(AreaEntity entity);

    /**
     * 找到所有子节点
     * @param entity
     * @return
     */
     List<AreaEntity> findByParentIdsLike(AreaEntity entity);

    /**
     * 更新所有父节点字段
     * @param entity
     * @return
     */
     int updateParentIds(AreaEntity entity);

    @Select("select * from sys_area a where del_flag = 0 and id =#{id}  and ROWNUM <=1")
    AreaEntity findAreaById(String id);
}
