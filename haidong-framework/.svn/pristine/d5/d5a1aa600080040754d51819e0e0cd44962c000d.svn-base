package com.huixin.framework.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

/**
 * 1:将JavaBean 转换成Map 2:将JSONObject 转换成Map 3:将Map 转换成JavaBean
 * 
 * @author cx
 */
public class MapUtil {

	/**
	 * 将json对象转换成Map
	 * 
	 * @param jsonObject
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(JSONObject jsonObject) {
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<String> iterator = jsonObject.keys();
		String key = null;
		Object value = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			value = jsonObject.get(key);
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 将map转换成Javabean(适用场合：beanClass存在默认构造器)
	 * 
	 * @param map
	 * @param beanClass
	 * @return
	 */
	public static Object toBean(Map<String, Object> map, Class<?> beanClass) {
		if (map == null || map.size() == 0 || beanClass == null) {
			return null;
		}
		Object bean;
		try {
			bean = beanClass.newInstance();// 调用默认构造器
		} catch (Exception e) {
			return null;
		}
		Method[] methods = beanClass.getDeclaredMethods();
		for (Method method : methods) {
			try {
				if (method.getName().startsWith("set")) {
					String methodName = method.getName();
					String fieldName = methodName.substring(3);
					fieldName = fieldName.toLowerCase().charAt(0) + fieldName.substring(1);
					method.invoke(bean, new Object[] { map.get(fieldName) });
				}
			} catch (Exception e) {
			}
		}

		return bean;
	}

	/**
	 * 将map转换成Javabean
	 * 
	 * @param map
	 * @param beanClass
	 * @return
	 */
	public static Object toBean(Map<String, Object> map, Object bean) {
		if (map == null || map.size() == 0 || bean == null) {
			return bean;
		}
		Method[] methods = bean.getClass().getDeclaredMethods();
		for (Method method : methods) {
			try {
				if (method.getName().startsWith("set")) {
					String methodName = method.getName().toLowerCase();
					String fieldName = methodName.substring(3);
					fieldName = fieldName.toLowerCase().charAt(0) + fieldName.substring(1);
					method.invoke(bean, new Object[] { map.get(fieldName) });
				}
			} catch (Exception e) {
			}
		}

		return bean;
	}

	/**
	 * 将javaBean转换成Map
	 * 
	 * @param javaBean
	 *            javaBean
	 * @return Map对象
	 */
	public static Map<String, Object> toMap(Object javaBean) {
		if (javaBean == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Method[] methods = javaBean.getClass().getDeclaredMethods();

		for (Method method : methods) {
			try {
				if (method.getName().startsWith("get")) {
					String methodName = method.getName();
					String fieldName = methodName.substring(3);
					fieldName = fieldName.toLowerCase().charAt(0) + fieldName.substring(1);

					Object value = method.invoke(javaBean, (Object[]) null);
					map.put(fieldName, value);
				}
			} catch (Exception e) {
			}
		}

		return map;
	}

	/**
	 * 将javaBean转换成Map
	 * 
	 * @param javaBean
	 *            javaBean
	 * @return Map对象
	 */
	public static Map<String, Object> toMap(Object javaBean, Map<String, Object> map) {
		if (javaBean == null) {
			return map;
		}
		Method[] methods = javaBean.getClass().getDeclaredMethods();

		for (Method method : methods) {
			try {
				if (method.getName().startsWith("get")) {
					String methodName = method.getName();
					String fieldName = methodName.substring(3);
					fieldName = fieldName.toLowerCase().charAt(0) + fieldName.substring(1);

					Object value = method.invoke(javaBean, (Object[]) null);
					map.put(fieldName, value);
				}
			} catch (Exception e) {
			}
		}

		return map;
	}

	/**
	 * 将javaBean转换成Map
	 * 
	 * @param javaBean
	 *            javaBean
	 * @return Map对象
	 */
	public static Map<String, Object> toMapWithoutNullValue(Object javaBean) {
		if (javaBean == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Method[] methods = javaBean.getClass().getDeclaredMethods();

		for (Method method : methods) {
			try {
				if (method.getName().startsWith("get")) {
					String methodName = method.getName();
					String fieldName = methodName.substring(3);
					fieldName = fieldName.toLowerCase().charAt(0) + fieldName.substring(1);

					Object value = method.invoke(javaBean, (Object[]) null);
					if (value != null && !"".equals(value)) {
						map.put(fieldName, value);
					}
				}
			} catch (Exception e) {
			}
		}

		return map;
	}

	/***
	 * 签到数据转换
	 * @param object
	 * @return
	 */
	public static Map<String, Object> toHashMap(Object object) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 将json字符串转换成jsonObject
		JSONObject jsonObject = JSONObject.fromObject(object);
		Iterator it = jsonObject.keys();
		// 遍历jsonObject数据，添加到Map对象
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			if (jsonObject.get(key) instanceof JSONObject) {
				Map<String, Object> data = toHashMap(jsonObject.get(key));
				result.put("data", data);
			} else if (jsonObject.get(key) instanceof Integer) {
				Integer value = (Integer) jsonObject.get(key);
				result.put(key, String.valueOf(value));
			} else {
				String value = (String) jsonObject.get(key);
				result.put(key, value);
			}

		}
		return result;
	}

	public static void main(String[] args) {
		/*
		 * User user=new User(1,"111111"); Map map=toMap(user,new HashMap());
		 * System.out.println(map.get("id"));
		 * System.out.println(map.get("name")); user=(User) toBean(map,
		 * User.class); System.out.println(user.getId());
		 * System.out.println(user.getName());
		 */
		Gson gson = new Gson();
		String json = "{\"code\":\"200\",\"data\":{\"countNum\":\"3\",\"photoUrl\":\"http://ocau360tj.bkt.clouddn.com/18d8ea134cb34b3a8177dd5883155260/f0a405ab790c470fb0aefe72bdf5e434\",\"userName\":\"大海哥\",\"gender\":\"1\",\"type\":\"2\",\"wish\":\"早生贵子，百年好合\",\"state\":0,\"userId\":\"18d8ea134cb34b3a8177dd5883155260\"},\"message\":\"success\",\"type\":\"login\"}";
		Map<String, Object> m = toHashMap(json);
		Map data =(Map) m.get("data");
		System.out.println(data.get("wish"));
	}
}
