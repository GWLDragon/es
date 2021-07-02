package com.hcf.nszh.provider.mz.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 根据key移除缓存
 * 
 * @author maruko
 * @date 2019年1月4日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RemoveCache {

	/**
	 * 需要删除的key的完整路径
	 * <p>
	 * <b>为添加PutCache注解的方法全路径</b>
	 * </p>
	 * 
	 * @return
	 */
	String key() default "";

	/**RedisAdvice.java
	 * 需要删除的key的完整路径
	 * <p>
	 * <b>为添加PutCache注解的方法名称</b>
	 * </p>
	 * <p>
	 * <b style="color:red;">前提条件必须要和PutCache的那个方法在同一个类中</b>
	 * </p>
	 * 
	 * @return
	 */
	String methodName() default "";
}