/**
 * 
 */
package com.huixin.framework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 *
 */
public class ImageUtils {

	public static void main(String args[]) throws Exception {

		String candidate = "http://www.investbank.cn/new/article/ArticleDetail.aspx?id=52998";
		String regex = "([0-9]+)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(candidate);
		String val = null;
		while (m.find()) {
			val = m.group();
			System.out.println("MATCH: " + val);
		}
	}
}
