package com.hcf.nszh.provider.mz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 〈一句话功能简述〉<br>
 * 〈参数详情〉
 *
 * @author maruko
 * @date 2021/7/6 17:07
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ParamsEntity {


    /**
     * 序号
     */
    private Long id;

    /**
     * sql序号
     */
    private Long sqlId;

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数值
     */
    private Object value;

    /**
     * 参数类型
     */
    private String type;

}
