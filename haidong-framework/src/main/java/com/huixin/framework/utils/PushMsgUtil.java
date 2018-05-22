package com.huixin.framework.utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.huixin.framework.exception.BusinessException;

public class PushMsgUtil {

	public final static String HAPPY_HOURS = "happyhour";

	public final static String PRINTER = "printer";

	public final static String ITEM = "item";

	public final static String MODIFIER = "modifier";

	public final static String USER = "user";

	public final static String RESTAURANT = "restaurant";

	public final static String PLACE_TABLE = "place_table";

	public final static String TAX = "tax";
	
	public final static String SETTLEMENT = "settlement";
	
	public final static String DELREVENUE="del_revenue"; // 删除Revenue Center
	
	public final static String RESTCONFIG="rest_config"; // 餐厅配置信息
	
	public final static String RESTAURANT_DESK = "restaurantDesk";
	
	/**
	 * 扫描桌子推送消息
	 * @param restId 餐厅id
	 * @param tableId 桌子号
	 * @param uniqueId 此次查询订单的唯一标识
	 * @param revenueId 收银中心Id
	 * @param msgKey
	 * @param restaurantDesk 
	 * @return
	 * @throws BusinessException 
	 */
	public static String sendRestaurantDesk(Integer restId,Integer tableId,Integer revenueId,String appId,String uniqueId, String msgKey) throws BusinessException {
		String pushUrl = ConfigHelper.getString("alfred.push.path");
		String str = HttpClientUtil.reqHttp(pushUrl+"/push/sendRestaurantDesk", new NameValuePair[]{
				new BasicNameValuePair("restId", restId.toString()),new BasicNameValuePair("tableId", tableId.toString()),
				new BasicNameValuePair("revenueId", revenueId.toString()),new BasicNameValuePair("appId", appId),new BasicNameValuePair("uniqueId", uniqueId),new BasicNameValuePair("msgContent", msgKey)});
		return str;
	}
	
	public static String sendRestaurant(Integer restId, String msgKey) throws BusinessException {
		String pushUrl = ConfigHelper.getString("alfred.push.path");
		String str = HttpClientUtil.reqHttp(pushUrl+"/push/sendRestaurant", new NameValuePair[]{
				new BasicNameValuePair("restId", restId.toString()),new BasicNameValuePair("msgContent", msgKey)});
		return str;
	}

	public static String sendRevenue(Integer revenueId, String msgKey) throws BusinessException {
		String pushUrl = ConfigHelper.getString("alfred.push.path");
		String str = HttpClientUtil.reqHttp(pushUrl+"/push/sendRevenue", new NameValuePair[]{
				new BasicNameValuePair("revenueId", revenueId.toString()),new BasicNameValuePair("msgContent", msgKey)});
		return str;
	}
	
	public static String refreshUserCache(Integer restaurantId,Integer userId) throws BusinessException {
		String apiUrl = ConfigHelper.getString("alfred.api.path");
		String str = HttpClientUtil.reqHttp(apiUrl+"/cache/refreshUserCache", new NameValuePair[]{
				new BasicNameValuePair("restaurantId", restaurantId.toString()),new BasicNameValuePair("userId", userId.toString())});
		return str;
	}
	
	public static void main(String[] args) {
//		System.out.println(sendRevenue(26, "aaa 阿斯顿"));
		//System.out.println(sendRestaurantDesk(18,18,"2312ddeew", RESTAURANT_DESK));
	}
}
