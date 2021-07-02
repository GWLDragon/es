package com.hcf.nszh.provider.mz.annotation;

import java.lang.annotation.*;

/**
 * 放入缓存
 *
 * @author lc
 * @date 2019年1月4日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface PutCache {
    String value() default "";

    /**
     * 缓存超时时间，单位：秒
     * @return
     */
    int flushTime() default 60;
}
