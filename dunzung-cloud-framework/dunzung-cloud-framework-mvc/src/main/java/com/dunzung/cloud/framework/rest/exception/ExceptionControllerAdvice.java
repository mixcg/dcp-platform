package com.dunzung.cloud.framework.rest.exception;

import com.dunzung.cloud.framework.exception.PortalException;
import com.dunzung.cloud.framework.rest.R;
import com.dunzung.cloud.framework.rest.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * Created by duanzj on 2018/9/14.
 */
@ResponseBody
@ControllerAdvice
public class ExceptionControllerAdvice {
    private static Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    public ExceptionControllerAdvice() {
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public R handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error("缺少请求参数", e);
        return R.error(ResponseCode.PARAM_ERROR.getCode(), ResponseCode.PARAM_ERROR.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public R handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("参数解析失败", e);
        return R.error(ResponseCode.JSON_ERROR.getCode(), ResponseCode.JSON_ERROR.getMessage());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public R handleServiceException(ConstraintViolationException e) {
        logger.error("参数验证失败", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        return R.error(ResponseCode.VALIDATION_ERROR.getCode(), message);
    }

    @ExceptionHandler({ValidationException.class})
    public R handleValidationException(ValidationException e) {
        logger.error("参数验证失败", e);
        return R.error(ResponseCode.VALIDATION_ERROR.getCode(), ResponseCode.VALIDATION_ERROR.getMessage());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public R handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("不支持当前请求方法", e);
        return R.error(ResponseCode.REQUEST_FORBIDDEN.getCode(), "不支持当前请求方法");
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public R handleHttpMediaTypeNotSupportedException(Exception e) {
        logger.error("不支持当前媒体类型", e);
        return R.error(ResponseCode.REQUEST_FORBIDDEN.getCode(), "不支持当前媒体类型");
    }

    @ExceptionHandler({PortalException.class})
    public R handleServiceException(PortalException e) {
        logger.error("业务逻辑异常", e);
        return R.error(ResponseCode.INTERNAL_ERROR.getCode(), ResponseCode.INTERNAL_ERROR.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public R handleException(Exception e) {
        logger.error("通用异常", e);
        return R.error(ResponseCode.INTERNAL_ERROR.getCode(), "通用异常");
    }
}