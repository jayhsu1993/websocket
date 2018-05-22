package com.huixin.framework.utils;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import net.sf.ezmorph.object.AbstractObjectMorpher;

public class TimestampToDateMorpher extends AbstractObjectMorpher {

	@Override
	public Object morph(Object value) {
		if (!StringUtils.isBlank(String.valueOf(value))) {
			return new Date(Long.parseLong(String.valueOf(value)));
		}
		return null;
	}
	
//	@Override
//	public Object morph(Object value) {
//		return new Date(Long.parseLong((String) value));
//	}

	@Override
	public Class morphsTo() {
		return Date.class;
	}

	public boolean supports(Class clazz) {
		return Long.class.isAssignableFrom(clazz);
	}
}
