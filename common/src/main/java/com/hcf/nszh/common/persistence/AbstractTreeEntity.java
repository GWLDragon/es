package com.hcf.nszh.common.persistence;

import com.hcf.nszh.common.supcan.treelist.SupTreeList;
import lombok.Data;

import java.io.Serializable;

/**
 * @author maruko
 * @Date 2019/7/1
 **/
@SupTreeList
@Data
public abstract class AbstractTreeEntity<T> implements Serializable {
    /**
     * 父级编号
     */
    protected T parent;

    /**
     * 所有父级编号
     */
    protected String parentIds;

    /**
     * 机构名称
     */
    protected String name;

    /**
     * 排序
     */
    protected Integer sort;
    private String officeId;
}
