package com.huixin.framework.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * json转换工具类
 * @author cx
 *
 */
public class JsonUtil {
	private static Log logger = LogFactory.getLog(JsonUtil.class);

	/**
	 * Map对象转化为JSON字符串
	 * 
	 * @param map
	 * @param resultCode
	 * @return
	 */
	public static String toJsonStr(Map<String,Object> resultMap) throws Exception{
		if (resultMap == null) {
			return null;
		}
		return JSONObject.fromObject(resultMap).toString();
	}

	/**
	 * jsonStr转换为Bean类
	 * 
	 * @param jsonString
	 * @param beanCalss
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBean(String jsonStr, Class<T> beanCalss, Map<String, Class> map) throws Exception{
		if (StringUtils.isBlank(jsonStr)) {
			logger.info("jsonStr is null.");
			return null;
		}
		JSONUtils.getMorpherRegistry().registerMorpher(new TimestampToDateMorpher());
		
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		if (map == null) {
			return (T) JSONObject.toBean(jsonObject, beanCalss);
		} else {
			return (T) JSONObject.toBean(jsonObject, beanCalss, map);
		}
	}

	/**
	 * HttpServletRequest转换为Bean类
	 * 
	 * @param request
	 * @param beanCalss
	 * @return
	 */
	public static <T> T toBean(HttpServletRequest request, Class<T> beanCalss) throws Exception{	
		String jsonStr = getJsonStrFromRequest(request);
		if (StringUtils.isBlank(jsonStr)) {
			logger.info("jsonStr is null.");
			return null;
		}
		return toBean(jsonStr, beanCalss,null);
	}
	
	public static <T> T toBean(HttpServletRequest request, Class<T> beanCalss, Map<String, Class> map) throws Exception{	
		String jsonStr = getJsonStrFromRequest(request);
		if (StringUtils.isBlank(jsonStr)) {
			logger.info("jsonStr is null.");
			return null;
		}
		return toBean(jsonStr, beanCalss, map);
	}

	/**
	 * resquest对象转换为jsonStr
	 * 
	 * @param request
	 * @return
	 */
	public static String getJsonStrFromRequest(HttpServletRequest request) throws Exception{
		DataInputStream in = null;
		request.setCharacterEncoding("utf-8");
		System.setProperty("sun.net.client.defaultConnectTimeout",String.valueOf(10000));
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		// String jsonStr = new String(br.readLine());
		String inputLine = null;
		StringBuffer sb = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			sb.append(inputLine).append("\n");
		}
		IOUtils.closeQuietly(in);
		logger.info("jsonStr="+sb.toString());
		logger.info("request.getContentType()="+request.getContentType());
		
		return sb.toString();
	}
}
