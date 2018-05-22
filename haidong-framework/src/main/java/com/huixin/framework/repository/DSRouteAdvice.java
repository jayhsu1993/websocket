package com.huixin.framework.repository;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class DSRouteAdvice implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {
		DSTreadLocal.setDsTypeContextHolder(DSType.CLOUND);
		Object ret = null;
		try{
			ret = invocation.proceed();
		}finally{
			DSTreadLocal.clearDsTypeContextHolder();	
		}
		return ret;
	}

}
