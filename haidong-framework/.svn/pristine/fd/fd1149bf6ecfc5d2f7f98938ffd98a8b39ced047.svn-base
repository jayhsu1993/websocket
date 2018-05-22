package com.huixin.framework.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.huixin.framework.exception.BusinessException;

public class HttpClientUtil {

	private static Log log = LogFactory.getLog(HttpClientUtil.class);

	public static String getHttp(String url, String token, Map<String, Object> paramMap){
		HttpClient  httpclient = new DefaultHttpClient();
		HttpGet httpGet = null;
		InputStreamReader isReader = null;
		BufferedReader reader = null;
		try {
			url += "?";
			for (String key : paramMap.keySet()) {
				url = url + key + "=" + paramMap.get(key) + "&";
			}
			httpGet = new HttpGet(url);
			httpGet.setHeader("token", token);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				log.info("Response content length: " + entity.getContentLength());
			}
			isReader = new InputStreamReader(entity.getContent(), HTTP.UTF_8);
			reader = new BufferedReader(isReader);
			String retMsg = IOUtils.toString(reader);
			return retMsg;
		} catch (Exception e) {
			log.error("HttpClient reqHttp error" + e);
			e.printStackTrace();
		} finally {
			closeResource(httpclient, isReader, reader);
		}
		return null;
	}
	
	public static String getHttp(String url, Map<String, Object> paramMap){
		HttpClient  httpclient = new DefaultHttpClient();
		HttpGet httpGet = null;
		InputStreamReader isReader = null;
		BufferedReader reader = null;
		try {
			url += "?";
			for (String key : paramMap.keySet()) {
				url = url + key + "=" + paramMap.get(key) + "&";
			}
			httpGet = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				log.info("Response content length: " + entity.getContentLength());
			}
			isReader = new InputStreamReader(entity.getContent(), HTTP.UTF_8);
			reader = new BufferedReader(isReader);
			String retMsg = IOUtils.toString(reader);
			return retMsg;
		} catch (Exception e) {
			log.error("HttpClient reqHttp error" + e);
			e.printStackTrace();
		} finally {
			closeResource(httpclient, isReader, reader);
		}
		return null;
	}

	public static String reqHttp(String url, String param) {
		StringBuffer sb = new StringBuffer();
		DefaultHttpClient httpclient = new DefaultHttpClient();
		InputStreamReader isReader = null;
		BufferedReader reader = null;
		HttpPost httppost = new HttpPost(url);

		try {
			StringEntity reqEntity = new StringEntity(param);
			reqEntity.setContentType("application/x-www-form-urlencoded");
			//设置请求的参数
			httppost.setEntity(reqEntity);
			//执行
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				log.info("Response content length: " + entity.getContentLength());
			}
			// 显示结果
			isReader = new InputStreamReader(entity.getContent(), HTTP.UTF_8);
			reader = new BufferedReader(isReader);
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			log.error("HttpClient reqHttp error" + e);
			e.printStackTrace();
		} finally {
			closeResource(httpclient, isReader, reader);
		}

		return sb.toString();
	}

	public static String reqHttp(String url, NameValuePair[] nameValue) throws BusinessException {
		List<NameValuePair> nameValueList = Arrays.asList(nameValue);
		HttpClient httpclient = new DefaultHttpClient ();
		HttpPost httppost = new HttpPost(url);
		InputStreamReader isReader = null;
		BufferedReader reader = null;
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValueList,HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				log.info("Response content length: " + entity.getContentLength());
			}
			isReader = new InputStreamReader(entity.getContent(), HTTP.UTF_8);
			reader = new BufferedReader(isReader);
			String retMsg = IOUtils.toString(reader);
			return retMsg;
		} catch (Exception e) {
			log.error("HttpClient reqHttp error" + e);
			e.printStackTrace();
		} finally {
			closeResource(httpclient, isReader, reader);
		}
		return null;
	}

	private static void closeResource(HttpClient  httpclient,InputStreamReader isReader, BufferedReader reader){
		if( reader!=null ){
			try{
				reader.close();
			}catch(Exception ex){
				log.error("关闭流句柄错误",ex);
			}
		}
		if( isReader!=null ){
			try{
				isReader.close();
			}catch(Exception ex){
				log.error("关闭流句柄错误",ex);
			}
		}
		if (httpclient != null) {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception e) {
				log.error("关闭链接错误",e);
			}
			httpclient = null;
		}
	}

	public static void main(String[] args) throws BusinessException {
		//{"Code":1,"Msg":"success","ApiUrl":"/API/users.ashx","ClientAction":"","Data":{}}
//		System.out.println(HttpClientUtil.reqHttp("http://2114.215.243.219:8088/API/users.ashx?op=createUser",
//				new NameValuePair[]{
//				new BasicNameValuePair("UUID", "test667"),
//				new BasicNameValuePair("nickName", "nickName666"),
//				new BasicNameValuePair("headImg", "headImg666")
//				})); 
//		String str = "{\"Code\":1,\"Msg\":\"success\",\"ApiUrl\":\"/API/users.ashx\",\"ClientAction\":\"\",\"Data\":{}}";
		PageData result = new PageData();
		String netUrl = "http://114.215.243.219:8088/API/";
		String str = HttpClientUtil.reqHttp(netUrl + "users.ashx?op=createUser",
				new NameValuePair[]{
						new BasicNameValuePair("UUID", "dsfdsfdsfdssz22"),
						new BasicNameValuePair("nickName", ""),
						new BasicNameValuePair("headImg", "")
				});
		if(null == str) {
			result.put("Code", "500");
			result.put("message", "连接出错");
		} else {
			result = PageData.toHashMap(str);
		}
		if(!"500".equals(result.get("Code")) && !"1".equals(result.get("Code"))){
			System.out.println("sucess");
		} else {
			System.out.println("fail");
		}
	}
}
