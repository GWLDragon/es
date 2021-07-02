package com.hcf.nszh.provider.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hcf.nszh.provider.system.api.dto.SearchOfficeDTO;
import com.hcf.nszh.provider.system.api.vo.AddressBookVo;
import com.hcf.nszh.provider.system.api.vo.OfficeOfUserVo;
import com.hcf.nszh.provider.system.api.vo.OfficeVo;
import com.hcf.nszh.provider.system.entity.OfficeEntity;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@CacheNamespace(flushInterval = 60000)
@Repository
public interface OfficeMapper extends BaseMapper<OfficeEntity> {

    List<OfficeEntity> findByChildOffice(@Param("id") String id);

    List<String> findParentOffice(@Param("id") String id);

    List<OfficeEntity> findOfficeByCode(@Param("code") String code);

    @Select("select * from sys_office t where del_flag = 0 and id = #{id} and ROWNUM<=1")
    OfficeEntity findOfficeById(String id);

    List<OfficeVo> listRootOffice();

    OfficeVo getOfficeById(String officeId);

    void deleteById(String officeId);

    List<OfficeEntity> findByParentIdsLike(OfficeEntity officeEntity);

    int updateParentIds(OfficeEntity entity);

    int update(OfficeEntity officeEntity);

    List<OfficeEntity> findList(OfficeEntity officeEntity);

    List<OfficeVo> searchOffice(@Param("searchOfficeDTO") SearchOfficeDTO searchOfficeDTO);

    int updateByPrimaryKeySelective(OfficeEntity entity);

    List<OfficeVo> getOfficeByParentId(String parentId);

     List<OfficeOfUserVo> officeOfUser(@Param("roleId") String roleId);

    List<AddressBookVo> getAddressBook(Page<AddressBookVo> page, @Param("officeIds") List<String> officeIds, @Param("search") String search, @Param("roleCode") String roleCode);

    List<String> getOfficeIdByParentId();

    List<OfficeVo> getRootOfficeMsg();

    int getAddressBookCount(@Param("officeIds") List<String> officeIds, @Param("search") String search);

    List<AddressBookVo>  getAddressBookByUserId(@Param("userIds") List<Long> userIds);

    List<OfficeEntity> getOfficeByIds(@Param("officeIds") List<String> officeIds);

    List<String> getOfficeChildIds(@Param("officeId") String officeId);

    List<OfficeVo> getOfficeIsExistByName(@Param("name") String name,@Param("parentId") String parentId,@Param("id") String id,@Param("code") String code);

}
