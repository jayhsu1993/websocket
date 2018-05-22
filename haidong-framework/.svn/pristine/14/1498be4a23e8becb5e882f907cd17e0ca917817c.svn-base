package com.huixin.framework.utils.business;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderUtils extends Thread {

	private static long orderNum = 0l;
	private static String date;

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			System.out.println(OrderUtils.getOrderNo("2000"));
			// Thread.sleep(1000);
		}
	}

	/**
	 * ��ɶ������
	 * seq 1000 
	 * @return
	 */
	public static synchronized String getOrderNo(String seq) {
		String str = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
		if (date == null || !date.equals(str)) {
			date = str;
			orderNum = 0L;
		}
		orderNum++;
		long orderNo = Long.parseLong((date)) * 10000;
		orderNo += orderNum;
		return seq + orderNo + "";
	}

}