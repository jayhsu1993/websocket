package com.huixin.framework.utils;

import org.springframework.context.ApplicationContext;

import com.huixin.framework.wx.util.PropertiesUtil;

/**
 * 项目名称：投资银行在线
 * 
 */
public class Const {
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	public static final String SESSION_USER = "sessionUser";
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String SESSION_menuList = "menuList"; // 当前菜单
	public static final String SESSION_allmenuList = "allmenuList"; // 全部菜单
	public static final String SESSION_QX = "QX";
	public static final String SESSION_userpds = "userpds";
	public static final String SESSION_USERROL = "USERROL"; // 用户对象
	public static final String SESSION_USERNAME = "USERNAME"; // 用户名
	public static final String TRUE = "T";
	public static final String FALSE = "F";
	public static final String LOGIN = "/login_toLogin.do"; // 登录地址
	public static final String MOBILE = "admin/config/MOBILE.txt"; // 移动端地址
	public static final String SYSNAME = "admin/config/SYSNAME.txt"; // 系统名称路径
	public static final String PAGE = "admin/config/PAGE.txt"; // 分页条数配置路径
	public static final String EMAIL = "admin/config/EMAIL.txt"; // 邮箱服务器配置路径
	public static final String SMS1 = "admin/config/SMS1.txt"; // 短信账户配置路径1
	public static final String SMS2 = "admin/config/SMS2.txt"; // 短信账户配置路径2
	public static final String FWATERM = "admin/config/FWATERM.txt"; // 文字水印配置路径
	public static final String IWATERM = "admin/config/IWATERM.txt"; // 图片水印配置路径
	public static final String WEIXIN = "admin/config/WEIXIN.txt"; // 微信配置路径
	public static final String MUSIC = "admin/config/MUSIC_PATH.txt"; // 音乐配置路径
	public static final String COMATTACHEMENTFILEPATH = "admin/config/COMATTACHEMENTFILEPATH.txt"; // 上传附件路径
	public static final String IMGHOSTPATH = "admin/config/IMGHOSTPATH.txt"; // 上传图片配置路径
	public static final String H5HOSTPATH = "admin/config/H5HOSTPATH.txt"; // h5项目发布路径
	public static final String FILEPATHIMG = "uploadFiles/uploadFrontImgs"; // 直播封面图片上传路径
	public static final String FILEPATHSHAREIMG = "uploadFiles/uploadShareImgs"; // 微信分享图片上传路径
	public static final String GAMEPRIZEPICPATH = "admin/game"; // 游戏奖项图片存储路径
	public static final String FILEPATHIMG_M = "uploadFiles/music/"; // 音乐上传路径
	public static final String FILEPATHWXT = "wxt/"; // 微信帖上传路径
	public static final String FILEPATHFILE = "uploadFiles/file/"; // 文件上传路径
	public static final String FILEPATHTWODIMENSIONCODE = "uploadFiles/twoDimensionCode/"; // 二维码存放路径
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(app)|(weixin)|(static)|(main)|(websocket)).*"; // 不对匹配该值的访问路径拦截（正则）
	public static final String NOTIFY_URL = "admin/config/NOTIFY_URL.txt"; // 阿里支付回调接口
	public static ApplicationContext WEB_APP_CONTEXT = null; // 该值会在web容器启动时由WebAppContextListener初始化

	/**
	 * APP Constants
	 */
	// app注册接口_请求协议参数)
	public static final String[] APP_REGISTERED_PARAM_ARRAY = new String[] { "userID", "userPWD", "Email", "Mobile",
			"token" };
	public static final String[] APP_REGISTERED_VALUE_ARRAY = new String[] { "用户名", "密码", "邮箱", "手机", "标示" };

	// app根据用户名获取会员信息接口_请求协议中的参数
	public static final String[] APP_GETAPPUSER_PARAM_ARRAY = new String[] { "USERNAME" };
	public static final String[] APP_GETAPPUSER_VALUE_ARRAY = new String[] { "用户名" };

	// app根据用户登录接口_请求协议中的参数
	public static final String[] APP_LOGINAPPUSER_PARAM_ARRAY = new String[] { "userID", "userPWD", "token" };
	public static final String[] APP_LOGINPPUSER_VALUE_ARRAY = new String[] { "用户名", "密码", "标示" };

	public static final String DEFAULTGOLD = "DEFAULTGOLD";// 默认嗨豆数
	public static final String H5URL = "H5URL";// h5url
	public static final String CHATURL = "CHATURL";// chaturl
	public static final String CHAT_URL_NEW = "CHAT_URL_NEW";// chaturl
	public static final String QINIUHOSTPATH = "QINIUHOSTPATH";// 七牛服务器地址
	public static final String QINIUHOSTPATH_NEW = "QINIUHOSTPATH_NEW";// 七牛服务器地址_新_生产
	public static final String APIURL = "APIURL";// API服务器地址
	public static final String ISWEIXINPAY = "ISWEIXINPAY"; // 判断是否支付真实份子钱数目
	public static final String GIFT_HIDE = "GIFT_HIDE"; // 判断是否支付开启发送礼物
	public static final String VERSION = "VERSION"; // 版本状态
	public static final String SERVICE_TEL = "SERVICE_TEL"; // 客服电话
	public static final String SHOPURL = "SHOPURL";//商城服务器地址
	public static final String DASHOST = "DASHOST";//微喜帖接口Alexander
	public static final String GAME_URL = "GAME_URL";//GAME服务器地址
	public static final String CHAT_LIST = "CHAT_LIST";//chat服务器内网地址
	public static final String CHAT_LIST_GONG = "CHAT_LIST_GONG";//chat服务器公网地址
	public static final String SIYI_GONG = "SIYI_GONG";//司仪服务器公网地址


	//手机注册默认头像
	public static final String USER_DEFAULT_URL = "USER_DEFAULT_URL";

}
