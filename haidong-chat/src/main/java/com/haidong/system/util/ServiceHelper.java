package com.haidong.system.util;

import com.huixin.framework.utils.Const;
import com.huixin.haidong.service.system.admin.menu.MenuService;
import com.huixin.haidong.service.system.admin.role.RoleService;



/**
 * @author Administrator
 * 获取Spring容器中的service bean
 */
public final class ServiceHelper {
	
	public static Object getService(String serviceName){
		//WebApplicationContextUtils.
		return Const.WEB_APP_CONTEXT.getBean(serviceName);
	}
	
	public static RoleService getRoleService(){
		return (RoleService) getService("roleService");
	}
	
	public static MenuService getMenuService(){
		return (MenuService) getService("menuService");
	}
}
