/**
 * 
 */
package com.huixin.framework.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * @author Administrator
 *
 */
public class ObjectUtils {

	public static String getObject(Object param) {
		String result = "";
		if (param instanceof Integer) {
			int value = ((Integer) param).intValue();
			result = String.valueOf(value);
		} else if (param instanceof String) {
			String s = (String) param;
			result = s;
		} else if (param instanceof Double) {
			double d = ((Double) param).doubleValue();
			result = String.valueOf(d);
		} else if (param instanceof Float) {
			float f = ((Float) param).floatValue();
			result = String.valueOf(f);
		} else if (param instanceof Long) {
			long l = ((Long) param).longValue();
			result = String.valueOf(l);
		} else if (param instanceof Boolean) {
			boolean b = ((Boolean) param).booleanValue();
			result = String.valueOf(b);
		} else if (param instanceof Date) {
			Date d = (Date) param;
			result = String.valueOf(d);
		}
		return result;
	}
	
	public static void main(String[] args) {
//		System.out.println(getObject("1"));
//		System.out.println(getObject(199));
//		System.out.println(getObject(877.98));
//		System.out.println(getObject(399.998f));
//		System.out.println(getObject(39923232l));
//		System.out.println(getObject(false));
//		System.out.println(getObject(new Date().toString()));
//		DecimalFormat format = new DecimalFormat("0.00");
//	       String abc ="100";
//	       String a = format.format(new BigDecimal(abc));
//	       System.out.println(a);
		System.out.println(getStringFormat("1"));
	}
	
	public static String getStringFormat(String param) {
		DecimalFormat format = new DecimalFormat("0.00");
		if(StringUtils.isEmpty(param)){
			return "0.00";
		}
	    return format.format(new BigDecimal(param));
	}
	
	public static float getFloatFormat(String param) {
		DecimalFormat format = new DecimalFormat("0.00");
		if(StringUtils.isEmpty(param)){
			return 0;
		}
	    return Float.valueOf(format.format(new BigDecimal(param))) ;
	}
	
	public static String getStringFormat(int param) {
		DecimalFormat format = new DecimalFormat("0.00");
	    return format.format(new BigDecimal(param));
	}
	
}
