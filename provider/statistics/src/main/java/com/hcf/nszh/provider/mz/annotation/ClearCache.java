package com.hcf.nszh.provider.mz.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 清空缓存
 * <p>
 * <b>注意：此清空并不是将redis上所以的缓存都清空，只是根据缓存id（或是当前执行类、注解标注类）的缓存清空</b>
 * </p>
 * 
 * @author lc
 * @date 2019年1月4日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ClearCache {

	/**
	 * 缓存的id
	 * <p>
	 * 一般为类全名，与PutCache成对，也和PutCache所在类的类名一致
	 * </p>
	 * 
	 * @return
	 */
	String cacheId() default "";
}