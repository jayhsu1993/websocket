//package com.huixin.framework.getui;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.gexin.rp.sdk.base.IPushResult;
//import com.gexin.rp.sdk.base.impl.AppMessage;
//import com.gexin.rp.sdk.base.impl.ListMessage;
//import com.gexin.rp.sdk.base.impl.SingleMessage;
//import com.gexin.rp.sdk.base.impl.Target;
//import com.gexin.rp.sdk.exceptions.RequestException;
//import com.gexin.rp.sdk.http.IGtPush;
//import com.gexin.rp.sdk.template.LinkTemplate;
//import com.gexin.rp.sdk.template.NotificationTemplate;
//
//public class AppPush {
//	// 定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
//	private static String appId = "u4BoHuYJoH9rPIsP7K4Rz6";		//haidong
//	private static String appKey = "xtQRsj60J26OjYd6cWZAa3";
//	private static String masterSecret = "ruvwABEsBc9g1Csznrbq5A";
////	private static String appId = "E73q5wJiPj8ww5X5qCSu19";		//getui Demo
////	private static String appKey = "JsT6nDRysC87VJx2SoUnR";
////	private static String masterSecret = "6ekGGhTjFt7JGAkqieerh1";
//	
//	
//	private static String host = "http://sdk.open.api.igexin.com/apiex.htm";
//
//	/**
//	 * 对指定应用群推消息
//	 * 
//	 * @throws IOException
//	 */
//	public static void pushAll(String[] params) throws IOException {
//
//		IGtPush push = new IGtPush(host, appKey, masterSecret);
//
//		// 定义"点击链接打开通知模板"，并设置标题、内容、链接
//		LinkTemplate template = linkTemplateDemo(params);
//
//		List<String> appIds = new ArrayList<String>();
//		appIds.add(appId);
//
//		// 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
//		AppMessage message = new AppMessage();
//		message.setData(template);
//		message.setAppIdList(appIds);
//		message.setOffline(true);
//		message.setOfflineExpireTime(1000 * 600);
//
//		IPushResult ret = push.pushMessageToApp(message);
//		System.out.println(ret.getResponse().toString());
//	}
//
//	/**
//	 * 推送单个用户
//	 * 
//	 * @params CID
//	 * @throws IOException
//	 */
//	public static void pushToSingle(String CID, String[] params) throws IOException {
//		IGtPush push = new IGtPush(host, appKey, masterSecret);
//		LinkTemplate template = linkTemplateDemo(params);
//		SingleMessage message = new SingleMessage();
//		message.setOffline(true);
//		// 离线有效时间，单位为毫秒，可选
//		message.setOfflineExpireTime(24 * 3600 * 1000);
//		message.setData(template);
//		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
//		message.setPushNetWorkType(0);
//		Target target = new Target();
//		target.setAppId(appId);
//		target.setClientId(CID);
//		// target.setAlias(Alias);
//		IPushResult ret = null;
//		try {
//			ret = push.pushMessageToSingle(message, target);
//		} catch (RequestException e) {
//			e.printStackTrace();
//			ret = push.pushMessageToSingle(message, target, e.getRequestId());
//		}
//		if (ret != null) {
//			System.out.println(ret.getResponse().toString());
//		} else {
//			System.out.println("服务器响应异常");
//		}
//	}
//	
//	/**
//	 * @param params
//	 * 0:推送标题 1:推送内容 2:跳转链接
//	 * @return
//	 */
//	public static LinkTemplate linkTemplateDemo(String[] params) {
//		LinkTemplate template = new LinkTemplate();
//		// 设置APPID与APPKEY
//		template.setAppId(appId);
//		template.setAppkey(appKey);
//		// 设置通知栏标题与内容
//
//		template.setTitle(params[0]);
//		template.setText(params[1]);
//		// 配置通知栏图标
//		template.setLogo("icon.png");
//		// 配置通知栏网络图标，填写图标URL地址
//		template.setLogoUrl("");
//		// 设置通知是否响铃，震动，或者可清除
//		template.setIsRing(true);
//		template.setIsVibrate(true);
//		template.setIsClearable(true);
//		// 设置打开的网址地址
//		template.setUrl(params[2]);
//		return template;
//	}
//
//	/**
//	 * 对指定列表用户推送消息
//	 * 
//	 * @params CIDS 用户客户端标识列表
//	 * @throws IOException
//	 */
//	public static void pushList(List CIDS,String[] params) throws IOException {
//		// 配置返回每个用户返回用户状态，可选
//		System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
//		IGtPush push = new IGtPush(host, appKey, masterSecret);
//		// 通知透传模板
//		NotificationTemplate template = notificationTemplateDemo(params);
//		ListMessage message = new ListMessage();
//		message.setData(template);
//		// 设置消息离线，并设置离线时间
//		message.setOffline(true);
//		// 离线有效时间，单位为毫秒，可选
//		message.setOfflineExpireTime(24 * 1000 * 3600);
//		// 配置推送目标
//		List targets = setTargetList(CIDS);
//
//		// taskId用于在推送时去查找对应的message
//		String taskId = push.getContentId(message);
//		IPushResult ret = push.pushMessageToList(taskId, targets);
//		System.out.println(ret.getResponse().toString());
//	}
//
//	public static List setTargetList(List<String> CIDS) {
//		List targets = new ArrayList();
//		for (String CID : CIDS) {
//			Target target = new Target();
//			target.setClientId(CID);
//			target.setAppId(appId);
//			targets.add(target);
//		}
//		return targets;
//	}
//	
//	/**
//	 * 
//	 * @params params
//	 * 0:推送标题  1:推送内容 2.透传内容
//	 * @return
//	 */
//	public static NotificationTemplate notificationTemplateDemo(String[] params) {
//		NotificationTemplate template = new NotificationTemplate();
//		// 设置APPID与APPKEY
//		template.setAppId(appId);
//		template.setAppkey(appKey);
//		// 设置通知栏标题与内容
//		template.setTitle(params[0]);
//		template.setText(params[1]);
//		// 配置通知栏图标
//		template.setLogo("icon.png");
//		// 配置通知栏网络图标
//		template.setLogoUrl("");
//		// 设置通知是否响铃，震动，或者可清除
//		template.setIsRing(true);
//		template.setIsVibrate(true);
//		template.setIsClearable(true);
//		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
//		template.setTransmissionType(1);
//		template.setTransmissionContent(params[2]);
//		return template;
//	}
//
//	public static void main(String[] args) throws IOException {
//		List CIDS = new ArrayList();
////		CIDS.add("37d4b9dbded3a73419c4a78355a83b24");	//getui Demo
//		CIDS.add("453a6ad236da1b43022f65a2f0197c0b");	//haidong
//		String[] params = {"Show time,game is beginning!", "快来参加吧！","{payload:{id:'1234567890'}}"};
////		pushAll(params);
////		pushToSingle("37d4b9dbded3a73419c4a78355a83b24", params);
//		pushList(CIDS, params);
//	}
//
//}
