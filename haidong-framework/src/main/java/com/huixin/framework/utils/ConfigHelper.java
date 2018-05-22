package com.huixin.framework.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * 配置文件读取工具
 * @author cx
 *
 */
public class ConfigHelper {

	private static final String CONFIG_PATH = "config.properties";

	private static PropertiesConfiguration config;

	static {
		try {
			config = new PropertiesConfiguration(CONFIG_PATH);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static Object getProperty(String name) {
		return config.getProperty(name);
	}
	
	
	public static String getString(String name) {
		return config.getString(name);
	}
	
	public static int setString(String key, String value) {
		int flag=0 ;
		config.setProperty(key, value);
		try {
			config.save();
			flag = 1;
		} catch (ConfigurationException e) {
			e.printStackTrace();
			return flag;
		}
		return flag;
	}
	
	public static byte[] getBytesFromBase64(String name) {
		String base64 = getString(name);
		if (base64 == null) {
			throw new NullPointerException("read " + CONFIG_PATH + " [" + name + "] is null, can not byte[]");
		}
		return Base64.decodeBase64(base64);
	}

	public static String[] getStringArray(String name) {
		return config.getStringArray(name);
	}

	public static boolean getBoolean(String name) {
		return config.getBoolean(name);
	}

	public static int getInt(String name) {
		return config.getInt(name);
	}

	public static int getInt(String name, int defaultValue) {
		try {
			return config.getInt(name);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static double getDouble(String name) {
		return config.getDouble(name);
	}
	
}
