/**
 * 
 */
package com.huixin.framework.wx.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.huixin.framework.redis.RedisUtil;

/**
 * @author Administrator
 *
 */
public class WxHttpUtils {

	// 获取userinfo的微信官方链接
	public static String USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo";

	// 获取access_token的微信官方链接
	public static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	
	//token
	public static String JSAPI_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
	
	//jsapi_ticket
	public static String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

	// 获取微信授权链接
	public static String OAUH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
			+ PropertiesUtil.getValue("wechat.properties", "mchappid") + "&redirect_uri="
			+ RedisUtil.getInstance(5).get("H5URL");
//			+ "http://e8ixewxyz0.proxy.qqbrowser.cc/haidong-h5/";
			//测试
//			+ "wx92c9d009290d521a&redirect_uri="
//			+ "http://4ac90be8.ngrok.natapp.cn/haidong-h5/";

	// 获取微信授权链接参数
	public static String OAUH_PARAMS = "&response_type=code&scope=snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect";

	// 获取微信不带授权链接参数
	public static String NOT_OAUH_PARAMS = "&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";
	
	public static String sendGET(String url, String param) {
		String result = "";// 访问返回结果
		BufferedReader read = null;// 读取访问结果
		try {
			// 创建url
			URL realurl = new URL(url + "?" + param);
			// 打开连接
			URLConnection connection = realurl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立连接
			connection.connect();
			// 获取所有响应头字段
			// Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段，获取到cookies等
			// for (String key : map.keySet()) {
			// System.out.println(key + "--->" + map.get(key));
			// }
			// 定义 BufferedReader输入流来读取URL的响应
			read = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;// 循环读取
			while ((line = read.readLine()) != null) {
				result += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (read != null) {// 关闭流
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}
