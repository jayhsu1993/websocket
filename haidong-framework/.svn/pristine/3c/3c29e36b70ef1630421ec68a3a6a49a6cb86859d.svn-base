package com.huixin.framework.repository;

import org.springframework.util.Assert;

public class DSTreadLocal {

	private static final ThreadLocal<DSType> dsTypeContextHolder = new ThreadLocal<DSType>(); 

	public static DSType getDsTypeContextHolder() {
		return dsTypeContextHolder.get();
	}
	
	public static void setDsTypeContextHolder(DSType dsType){
		Assert.notNull(dsType);
		dsTypeContextHolder.set(dsType);
	}
	
	public static void clearDsTypeContextHolder(){
		dsTypeContextHolder.remove();
	}
}

