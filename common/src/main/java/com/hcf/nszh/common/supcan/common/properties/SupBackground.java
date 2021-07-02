package com.hcf.nszh.common.supcan.common.properties;

import java.lang.annotation.*;

/**
 * 硕正Background注解
 * @author maruko
 * @version 2013-11-12
 */
@Target({ ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SupBackground {
	
	/**
	 * 背景颜色
	 * @return
	 */
	String bgColor() default "";

}
