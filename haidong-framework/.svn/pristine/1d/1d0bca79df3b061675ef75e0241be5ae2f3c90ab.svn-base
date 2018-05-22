package com.huixin.framework.utils;

public class ParamHelper {
	public static String getPrintOrderNO(int orderNO) {
		String preStr = String.format("%08d", orderNO);
		return preStr;
	}

	public static String getPrintOrderBillNo(int prefix, int orderBillNo) {
		String preStr = String.format("%02d%06d",prefix,orderBillNo);
		return preStr;
	}
	
	/**
	 * 解析订单编号
	 * Integer[0]为次序id，Integer[1]为当前订单编号
	 * 
	 * @param billNumber
	 * @return
	 */
	public static Integer[] parseBillNumber(Integer billNumber) {
		if(billNumber==null||billNumber>99999999) {
			return null;
		}
		String billNumberStr = String.format("%08d", billNumber);
		String substring1 = billNumberStr.substring(0,2);
		String substring2 = billNumberStr.substring(2);
		Integer[] intAyy = new Integer[2];
		intAyy[0]=Integer.valueOf(substring1);
		intAyy[1]=Integer.valueOf(substring2);
		return intAyy;
	}
}
