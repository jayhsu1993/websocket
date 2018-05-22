package com.huixin.framework.exception;

import com.huixin.framework.redis.RedisUtil;
import com.huixin.framework.utils.PageData;

/**
 * 
 * 业务异常
 * 
 * @author slg
 * 
 */
public class BusinessException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2100269007285961068L;

	private String errorCode;

	private String errorMessage;
	
	private Object pd;
	
	private PageData pageData;
	
	private String version;

	public BusinessException(String errorCode, String errorMessage, Object pd) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.version = RedisUtil.getInstance(5).get("APP_VERSION");
		this.pd = pd;
	}
	
	public BusinessException(String errorCode, String errorMessage, PageData pageData) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.version = RedisUtil.getInstance(5).get("APP_VERSION");
		this.setPageData(pageData);
	}
	
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Object getPd() {
		return pd;
	}
	public void setPd(Object pd) {
		this.pd = pd;
	}

	public PageData getPageData() {
		return pageData;
	}

	public void setPageData(PageData pageData) {
		this.pageData = pageData;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	

}
