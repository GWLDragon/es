package com.hcf.nszh.provider.mz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author gwl
 * @date 2021/6/25 16:22
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
public class TestEntity implements Serializable {
    private  String name;
    private int age;
}
