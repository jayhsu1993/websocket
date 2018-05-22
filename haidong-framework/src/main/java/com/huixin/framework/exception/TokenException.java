package com.huixin.framework.exception;

import com.huixin.framework.redis.RedisUtil;

/**
 * 
 * 业务异常
 * 
 * @author slg
 * 
 */
public class TokenException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2100269007285961068L;

	private String errorCode;

	private String errorMessage;
	
	private String version;

	public TokenException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.version = RedisUtil.getInstance(5).get("APP_VERSION");
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}
