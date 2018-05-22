package com.huixin.framework.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.huixin.framework.enums.ResponseErrorEnum;
import org.springframework.http.HttpStatus;

/**
 * ResponseResult类 统一请求返回结果model
 *
 * @author Lance
 * @date 2017/3/1
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseResult<T> {

    private boolean success;
    private String message;
    private T data;
    /* 不提供直接设置errorCode的接口，只能通过setErrorInfo方法设置错误信息 */
    private String errorCode;

    private HttpStatus statusCode;

    public static <T> ResponseResult<T> newInstance() {
        return new ResponseResult<T>();
    }

    /**
     * 设置错误信息
     */
    public void setErrorInfo(ResponseErrorEnum responseErrorEnum) {
        this.errorCode = responseErrorEnum.getCode();
        this.message = responseErrorEnum.getMessage();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}
