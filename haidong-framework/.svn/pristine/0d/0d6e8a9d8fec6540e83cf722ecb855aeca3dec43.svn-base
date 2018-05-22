package com.huixin.framework.utils;

import java.text.DecimalFormat;

/**
 * 字符串相关方法
 *
 */
public class StringUtil {

	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * 
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr) {
		int i = 0;
		String TempStr = valStr;
		String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
		valStr = valStr + ",";
		while (valStr.indexOf(',') > 0) {
			returnStr[i] = valStr.substring(0, valStr.indexOf(','));
			valStr = valStr.substring(valStr.indexOf(',') + 1, valStr.length());

			i++;
		}
		return returnStr;
	}

	public static String getFloat(float f) {
		DecimalFormat decimalFomat = new DecimalFormat(".00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		return decimalFomat.format(f);// format 返回的是字符串
	}

}
