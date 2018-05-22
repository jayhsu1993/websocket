package com.huixin.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 常用的随机数生成方法
 * 
 * @author wuxiang date:2016.8.26
 *
 */
public class RandomUtil {
	public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String NUMBERCHAR = "0123456789";
	static int[] in = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	/**
	 * 返回一个定长的随机字符串(只包含大小写字母、数字)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateMixString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(LETTERCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateLowerString(int length) {
		return generateMixString(length).toLowerCase();
	}

	/**
	 * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateUpperString(int length) {
		return generateMixString(length).toUpperCase();
	}

	/**
	 * 生成一个定长的纯0字符串
	 * 
	 * @param length
	 *            字符串长度
	 * @return 纯0字符串
	 */
	public static String generateZeroString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append('0');
		}
		return sb.toString();
	}

	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 * 
	 * @param num
	 *            数字
	 * @param fixdlenth
	 *            字符串长度
	 * @return 定长的字符串
	 */
	public static String toFixdLengthString(long num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
		}
		sb.append(strNum);
		return sb.toString();
	}

	/**
	 * 每次生成的len位数都不相同
	 * 
	 * @param param
	 * @return 定长的数字
	 */
	public static int getNotSimple(int[] param, int len) {
		Random rand = new Random();
		for (int i = param.length; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = param[index];
			param[index] = param[i - 1];
			param[i - 1] = tmp;
		}
		int result = 0;
		for (int i = 0; i < len; i++) {
			result = result * 10 + param[i];
		}
		return result;
	}

	/**
	 * 生成100000~99999999之间的随机数
	 * 
	 * @return
	 */
	public static String getRandom() {
		Random rand = new Random();
		int n = 100000 + rand.nextInt(99900000);

		return Integer.toString(n);
	}

	/**
	 * 产生随机的2位数
	 * 
	 * @return
	 */
	public static String getTwo() {
		Random rad = new Random();
		String result = rad.nextInt(100) + "";
		if (result.length() == 1) {
			result = "0" + result;
		}
		return result;
	}

	public static void main(String[] args) {
		// System.out.println("返回一个定长的随机字符串(只包含大小写字母、数字):" +
		// generateString(10));
		// System.out.println("返回一个定长的随机纯字母字符串(只包含大小写字母):" +
		// generateMixString(10));
		// System.out.println("返回一个定长的随机纯大写字母字符串(只包含大小写字母):" +
		// generateLowerString(10));
		// System.out.println("返回一个定长的随机纯小写字母字符串(只包含大小写字母):" +
		// generateUpperString(10));
		// System.out.println("生成一个定长的纯0字符串:" + generateZeroString(10));
		// System.out.println("根据数字生成一个定长的字符串，长度不够前面补0:" +
		// toFixdLengthString(123, 10));
		//
		// System.out.println("每次生成的len位数都不相同:" + getNotSimple(in, 2));
		// System.out.println(getLiveId());
		getTradeId();

	}

	/**
	 * 生成房间号
	 * 
	 * @return
	 */
	public static String getLiveId() {
		String time = new SimpleDateFormat("ddHHmmss").format(new Date());
		String liveId = time + Integer.toString(getNotSimple(in, 2));
		return liveId;
	}

	/**
	 * 生成交易流水号
	 * 
	 * @return
	 */
	public static String getTradeId() {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String seconds = new SimpleDateFormat("HHmmss").format(new Date());
		return date + "1001" + getTwo() + "00" + seconds + getTwo();
	}

}
