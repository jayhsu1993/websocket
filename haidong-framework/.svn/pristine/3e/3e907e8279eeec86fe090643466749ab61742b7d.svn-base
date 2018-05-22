package com.huixin.framework.base;

import com.huixin.framework.enums.ResponseErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;


/**
 * Created by admin on 2017/1/20.
 *
 * 统一的RestController结果生成器
 */
public class RestResultGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestResultGenerator.class);

    /**
     * 生成响应成功的(不带正文)的结果
     *
     * @param message 成功提示信息
     * @return ResponseResult
     */
    public static ResponseResult genResult(String message) {

        ResponseResult result = ResponseResult.newInstance();
        result.setSuccess(true);
        result.setMessage(message);
        result.setStatusCode(HttpStatus.OK);
        return result;
    }

    /**
     * 生成响应成功(带正文)的结果
     *
     * @param data    结果正文
     * @param message 成功提示信息
     * @return ResponseResult<T>
     */
    public static <T> ResponseResult<T> genResult(T data, String message) {

        ResponseResult<T> result = ResponseResult.newInstance();
        result.setSuccess(true);
        result.setData(data);
        result.setMessage(message);
        result.setStatusCode(HttpStatus.OK);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("--------> result:{}", message);
        }

        return result;
    }

    /**
     * 生成响应失败的结果
     *
     * @param message 自定义错误信息
     * @return ResponseResult
     */
    public static ResponseResult genErrorResult(String message) {

        ResponseResult result = ResponseResult.newInstance();
        result.setSuccess(false);
        result.setMessage(message);
        result.setStatusCode(HttpStatus.BAD_REQUEST);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("--------> result:{}", JacksonMapper.toJsonString(result));
        }

        return result;
    }

    /**
     * 生成响应失败(带errorCode)的结果
     *
     * @param responseErrorEnum 失败信息
     * @return ResponseResult
     */
    public static ResponseResult genErrorResult(ResponseErrorEnum responseErrorEnum) {

        ResponseResult result = ResponseResult.newInstance();
        result.setSuccess(false);
        result.setErrorInfo(responseErrorEnum);
        result.setStatusCode(HttpStatus.BAD_REQUEST);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("--------> result:{}", JacksonMapper.toJsonString(result));
        }

        return result;
    }
}
