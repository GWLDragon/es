package com.hcf.nszh.common.utils;

/**
 * 关于异常的工具类.
 * @author maruko
 * @version 2013-01-15
 */
public class Exceptions {

	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}


	
}
