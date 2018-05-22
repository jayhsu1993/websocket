package com.huixin.framework.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 正则表达式帮助类
 * 
 * @author slg
 *
 */
public class RegexHelper {

	/**
	 * 利用正则表达式从文本text中找到夹在两个关键字中间的部分(keyword1)(.+)(keyword2)
	 * 
	 * @param text
	 * @param keyword1
	 * @param keyword2
	 * @return
	 */
	public static final String getStrBetweenKeywords(String text, String keyword1, String keyword2) {
		Pattern p = Pattern.compile(new StringBuffer().append("(").append(keyword1).append(")").append("(.+)")
				.append("(").append(keyword2).append(")").toString(), Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(text);
		while (m.find()) {
			return m.group(2);
		}
		return null;
	}

	/**
	 * 利用正则表达式从文本text中找到夹在两个关键字中间的部分(keyword1)(.+?)(keyword2)，懒惰模式匹配
	 * 
	 * @param text
	 * @param keyword1
	 * @param keyword2
	 * @return
	 */
	public static final String[] getLazyStrBetweenKeywords(String text, String keyword1, String keyword2) {
		String[] ret = new String[0];
		Set<String> tmp = new HashSet<String>();
		Pattern p = Pattern.compile(new StringBuffer().append("(").append(keyword1).append(")").append("(.+?)")
				.append("(").append(keyword2).append(")").toString(), Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(text);
		while (m.find()) {
			tmp.add(m.group(2));
		}
		return tmp.toArray(ret);
	}

	/**
	 * 利用正则表达式检查是否完整匹配
	 * 
	 * @param text
	 * @param reg
	 * @return
	 */
	public static final boolean isMatch(String text, String reg) {
		if (StringUtils.isNotEmpty(text) && StringUtils.isNotBlank(reg)) {
			Pattern p = Pattern.compile(reg);
			Matcher m = p.matcher(text);
			return m.matches();
		}
		return false;
	}

	/**
	 * 利用正则表达式检查是否包含
	 * 
	 * @param text
	 * @param reg
	 * @return
	 */
	public static final boolean isContain(String text, String reg) {
		if (StringUtils.isNotEmpty(text) && StringUtils.isNotBlank(reg)) {
			Pattern p = Pattern.compile(reg);
			Matcher m = p.matcher(text);
			return m.find();
		}
		return false;
	}

	public static final String getTopicIdByUrl(String url) {
		String regex = "([0-9]+)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(url);
		String result = "0";
		while (m.find()) {
			result = m.group();
		}
		return result;
	}
	
	public static boolean isNumeric(String str){ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	 } 
	
	public static void main(String[] args) {
		 String str="127.0.0.1";
		 System.out.println(str.lastIndexOf("."));
         str = str.replaceAll(str.substring(str.lastIndexOf("."), str.length()), ".**");
         System.out.println(str);
	}
	

}
