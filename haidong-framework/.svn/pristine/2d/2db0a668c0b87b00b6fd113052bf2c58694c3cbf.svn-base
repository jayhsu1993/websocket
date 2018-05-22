package com.huixin.framework.utils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.huixin.framework.exception.BusinessException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PageData extends HashMap implements Map, Serializable {

	private static final long serialVersionUID = 1L;

	Map map = null;
	HttpServletRequest request;
	
	public static void main(String[] args) {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("liveId", "aa11");
		jsonObject.put("gameId", "hdGame1");
		System.out.println(jsonObject);
	}

	public PageData(HttpServletRequest request) {
		this.request = request;
		Map properties = request.getParameterMap();
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, xssEncode(value));
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> tokenMap = (Map<String, Object>) request.getAttribute("data");
		if(null != tokenMap) {
			returnMap.put("USER_ID", tokenMap.get("uid"));
		}
		returnMap.put("IP",RequestUtil.getRemoteHost(request)); // IP;
		map = returnMap;
	}

	public PageData() {
		map = new HashMap();
	}

	@Override
	public Object get(Object key) {
		Object obj = null;
		if (map.get(key) instanceof Object[]) {
			Object[] arr = (Object[]) map.get(key);
			obj = request == null ? arr : (request.getParameter((String) key) == null ? arr : arr[0]);
		} else {
			obj = map.get(key);
		}
		return obj;
	}

	public String getString(Object key) {
		String result = (String) get(key);
		if (null == result) {
			return result;
		} else {
			return result.trim();
		}
	}

	public BigDecimal getBigDecimal(Object key){
		return (BigDecimal) get(key);
	}

	public BigDecimal getBigDecimalFromString(Object key){
		return new BigDecimal(getString(key));
	}

	public Double getDoubleFromString(Object key){
		return Double.valueOf(getString(key));
	}

	public Double getDoubleFromBigDecimal(Object key){
		BigDecimal bigDecimal = getBigDecimal(key);
		return bigDecimal.doubleValue();
	}

	public int getInt(Object key) {
		return (Integer) get(key);
	}
	
	public int getInt1(Object key) {
		String str = getString(key);
		if(StringUtils.isEmpty(str)) {
			return 0;
		}
		return Integer.valueOf(str);
	}
	
	public float getFloat(Object key) {
		String str = getString(key);
		if(StringUtils.isEmpty(str)) {
			return 0.0f;
		}
		return Float.valueOf(str);
	}
	
	public float getFloat1(Object key) {
		return ((Float) get(key)).floatValue();
	}
	
	public Double getDouble(Object key) {
		String str = getString(key);
		if(StringUtils.isEmpty(str)) {
			return 0.0;
		}
		return Double.valueOf(str);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object put(Object key, Object value) {
		return map.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return map.containsValue(value);
	}

	public Set entrySet() {
		// TODO Auto-generated method stub
		return map.entrySet();
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return map.isEmpty();
	}

	public Set keySet() {
		// TODO Auto-generated method stub
		return map.keySet();
	}

	@SuppressWarnings("unchecked")
	public void putAll(Map t) {
		// TODO Auto-generated method stub
		map.putAll(t);
	}

	public int size() {
		// TODO Auto-generated method stub
		return map.size();
	}

	public Collection values() {
		// TODO Auto-generated method stub
		return map.values();
	}
	

	private String escape(String s) {
		StringBuilder sb = new StringBuilder(s.length() + 16);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '>':
				sb.append('＞');// 全角大于号
				break;
			case '<':
				sb.append('＜');// 全角小于号
				break;
			case '\'':
				sb.append('‘');// 全角单引号
				break;
			case '\"':
				sb.append('“');// 全角双引号
				break;
			case '\\':
				sb.append('＼');// 全角斜线
				break;
			case '%':
				sb.append('％'); // 全角冒号
				break;
			default:
				sb.append(c);
				break;
			}

		}
		return sb.toString();
	}

	/**
	 * 将容易引起xss漏洞的半角字符直接替换成全角字符
	 * 
	 * @param s
	 * @return
	 */
	private String xssEncode(String s) {
		if (s == null || s.isEmpty()) {
			return s;
		}

		String result = stripXSS(s);
//		if (null != result) {
//			result = escape(result);
//		}

		return result;
	}

	private String stripXSS(String value) {
		if (value != null) {
			// NOTE: It's highly recommended to use the ESAPI library and
			// uncomment the following line to
			// avoid encoded attacks.
			// value = ESAPI.encoder().canonicalize(value);
			// Avoid null characters
			value = value.replaceAll("", "");
			// Avoid anything between script tags
			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid anything in a src='...' type of expression
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Remove any lonesome </script> tag
			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<script(.*?)>",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid eval(...) expressions
			scriptPattern = Pattern.compile("eval\\((.*?)\\)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid expression(...) expressions
			scriptPattern = Pattern.compile("expression\\((.*?)\\)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid javascript:... expressions
			scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid vbscript:... expressions
			scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid onload= expressions
			scriptPattern = Pattern.compile("onload(.*?)=",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			scriptPattern = Pattern.compile("<iframe>(.*?)</iframe>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");

			scriptPattern = Pattern.compile("</iframe>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<iframe(.*?)>",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
		}
		return value;
	}
	
	/**
	 * 将json格式的字符串解析成Map对象
	 * <li>json格式：{"name":"admin","retries":"3fff","testname"
	 * :"ddd","testretries":"fffffffff"}
	 */
	public static PageData toHashMap(Object object) throws BusinessException{
		try {
			JSONObject js = JSONObject.fromObject(object);
			PageData data = new PageData();
			return populate(js, data);
		} catch (Exception e) {
			throw new BusinessException("jsonError", "转换json异常:", object);
		}
	}

	private static PageData populate(JSONObject jsonObject, PageData map) throws BusinessException{
		for (Iterator iterator = jsonObject.entrySet().iterator(); iterator.hasNext();) {
			String entryStr = String.valueOf(iterator.next());
			String key = entryStr.substring(0, entryStr.indexOf("="));
			String value = entryStr.substring(entryStr.indexOf("=") + 1, entryStr.length());
			if (jsonObject.get(key).getClass().equals(JSONObject.class)) {
				PageData _map = new PageData();
				map.put(key, _map);
				populate(jsonObject.getJSONObject(key), ((PageData) (_map)));
			} else if (jsonObject.get(key).getClass().equals(JSONArray.class)) {
				ArrayList list = new ArrayList();
				map.put(key, list);
				populateArray(jsonObject.getJSONArray(key), list);
			} else {
				map.put(key, jsonObject.get(key));
			}
		}
		return map;
	}

	private static void populateArray(JSONArray jsonArray, List list) throws BusinessException {
		for (int i = 0; i < jsonArray.size(); i++)
			if (jsonArray.get(i).getClass().equals(JSONArray.class)) {
				ArrayList _list = new ArrayList();
				list.add(_list);
				populateArray(jsonArray.getJSONArray(i), _list);
			} else if (jsonArray.get(i).getClass().equals(JSONObject.class)) {
				PageData _map = new PageData();
				list.add(_map);
				populate(jsonArray.getJSONObject(i), _map);
			} else {
				list.add(jsonArray.get(i));
			}
	}

}
