package com.haidong.system.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.huixin.framework.exception.BusinessException;
import com.huixin.framework.exception.TokenException;
import com.huixin.framework.utils.Logger;


@ControllerAdvice
public class GlobalExceptionHandler {

	protected Logger logger = Logger.getLogger(this.getClass());
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(SQLException.class)
	public @ResponseBody Map<String, Object> handleSQLException(Exception ex) {
		logger.error("handleSQLException", ex);
		Map<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("code", "90003");
		errorMap.put("message", "数据操作异常");
		return errorMap;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IOException.class)
	public @ResponseBody Map<String, Object> handleIOException(Exception ex) {
		logger.error("handleIOException", ex);
		Map<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("code", "90002");
		errorMap.put("message", "文件操作异常");
		return errorMap;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	public @ResponseBody Map<String, Object> handleException(Exception ex) {
		logger.error("handleException", ex);
		Map<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("code", "90001");
		errorMap.put("message", "系统异常");
		return errorMap;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BusinessException.class)
	public @ResponseBody Map<String, Object> handleBusinessException(BusinessException ex) {
		logger.error("handleBusinessException", ex);
		Map<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("code", ex.getErrorCode());
		errorMap.put("message", ex.getErrorMessage());
		return errorMap;
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(TokenException.class)
	public @ResponseBody Map<String, Object> handleITokenException(TokenException ex) {
		logger.error("handleITokenException", ex);
		Map<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("code", ex.getErrorCode());
		errorMap.put("message", ex.getErrorMessage());
		return errorMap;
	}
}
