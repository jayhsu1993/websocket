/**
 * 
 */
package com.huixin.framework.utils;

import java.util.UUID;

/**
 * @author 兴园
 *
 */
public class GUIDUtil {
	
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		// 得到对象产生的ID
		String result = uuid.toString();
		// 转换为大写
		result = result.toUpperCase().replaceAll("-", "");
		return result;
	}
}
