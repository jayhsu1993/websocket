package com.huixin.framework.repository;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

/**
 * 扩展SqlSessionFactoryBean，用于可以自动化从配置中心中获取配置项来加载配置路劲  
 *
 */
public class CustomSqlSessionFactoryBean extends SqlSessionFactoryBean implements ApplicationContextAware{

	//加载配置路径项，采用","分割的形式
	private String scanClassPath;
	private ApplicationContext applicationContext;
	
	public void afterPropertiesSet() throws Exception {
		this.parseScanClassPath();
		super.afterPropertiesSet();
	}
	
	/**
	 * 解析配置路径想，生成加载资源路径
	 */
	private void parseScanClassPath(){
		try{
			System.out.println("解析路径：" + this.scanClassPath);
			String[] paths = scanClassPath.split(",");
			if( paths!=null && paths.length>0 ){
				List<Resource> scanResource = new ArrayList<Resource>();
				for(int i=0;i<paths.length;i++ ){
					Resource[] temp = applicationContext.getResources(paths[i]);
					for(Resource r:temp){
						scanResource.add(r);
					}
				}
				Resource[] resources = new Resource[scanResource.size()];
				scanResource.toArray(resources);
				this.setMapperLocations(resources);
			}
		}catch(Exception ex){
			throw new RuntimeException("解析scanClassPatch路径错误");
		}
	}

	/**
	 * @return the scanClassPath
	 */
	public String getScanClassPath() {
		return scanClassPath;
	}

	/**
	 * @param scanClassPath the scanClassPath to set
	 */
	public void setScanClassPath(String scanClassPath) {
		this.scanClassPath = scanClassPath;
	}

	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
		this.applicationContext = applicationContext;
	}
	
}
