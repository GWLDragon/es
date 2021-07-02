package com.hcf.nszh.provider.system.utils;

import org.springframework.beans.BeanUtils;

/**
 * Created by Furant
 * 2019/7/3 22:49
 */
public class ObjConvert {
    public  static <T> T newInstance(Object src, Class<T> clazz) {
        T t = newInstance(clazz);
        copyProperties(src, t);
        return t;
    }

    public static  <T> T newInstance(Class<T> tClass) {
        T target = BeanUtils.instantiateClass(tClass);
        return target;
    }

    public static void copyProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target);
    }
}
