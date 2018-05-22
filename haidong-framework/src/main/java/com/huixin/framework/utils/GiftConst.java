package com.huixin.framework.utils;

/**
 * 定义礼物类型常量
 * @author wuxiang
 * date：2016.8.28
 *
 */
public class GiftConst {
	public static final String language = "5";		//祝福语对应嗨币
	public static final String red1 = "66";			//红包1
	public static final String red2 = "88";			//红包2
	public static final String angel = "10";		//天使之心
	public static final String flower = "10";		//花好月圆
	public static final String car = "50";			//法拉利
	public static final String tanker = "60";		//游艇
	public static final String cupid = "100";		//丘比特之心
	public static final String Domineering_LG1 = "0.5";		//霸气弹幕1
	public static final String Domineering_LG2 = "1.2";		//霸气弹幕2
	public static final String Domineering_LG3 = "2.0";		//霸气弹幕3

	/**
	 * 获取该礼物对应的金额
	 * @param RoomId
	 * @return
	 */
	public static String getGiftMoney(String giftId){	
		String giftMoney = null;
		switch(giftId){
			case "language":
				giftMoney = GiftConst.language;
				break;
			case "red1":
				giftMoney = GiftConst.red1;
				break;
			case "red2":
				giftMoney = GiftConst.red2;
				break;
			case "angel":
				giftMoney = GiftConst.angel;
				break;
			case "flower":
				giftMoney = GiftConst.flower;
				break;
			case "car":
				giftMoney = GiftConst.car;
				break;
			case "cupid":
				giftMoney = GiftConst.cupid;
				break;
			case "tanker":
				giftMoney = GiftConst.tanker;
				break;
			case "Domineering_LG1":
				giftMoney = GiftConst.Domineering_LG1;
				break;
			case "Domineering_LG2":
				giftMoney = GiftConst.Domineering_LG2;
				break;
			case "Domineering_LG3":
				giftMoney = GiftConst.Domineering_LG3;
				break;
		}
		return giftMoney;
	}


}
