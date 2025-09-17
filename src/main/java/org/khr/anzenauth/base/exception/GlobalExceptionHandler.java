package org.khr.anzenauth.base.exception;


import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.khr.anzenauth.base.common.BaseResponse;
import org.khr.anzenauth.base.common.ErrorCode;
import org.khr.anzenauth.base.common.ResultUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
@Hidden // 忽略此接口在swagger文档中显示
public class GlobalExceptionHandler {


    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public BaseResponse<?> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',权限校验失败'{}'", requestURI, e.getMessage());
        return ResultUtils.error(ErrorCode.NO_AUTH_ERROR, "没有权限，请联系管理员授权");
    }



    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "系统错误");
    }

    // region  validate校验异常处理

    /**
     * 处理请求参数格式错误 @RequestBody上使用@Valid 实体上使用@NotNull等，验证失败后抛出的异常是MethodArgumentNotValidException异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<?> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return ResultUtils.error(ErrorCode.PARAMS_ERROR, message);
    }

    /**
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     */
    @ExceptionHandler(BindException.class)
    public BaseResponse<?> BindExceptionHandler(BindException e) {
        String message = e.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return ResultUtils.error(ErrorCode.PARAMS_ERROR, message);

    }

    /**
     * 参数格式异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResponse<?> HttpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        return ResultUtils.error(ErrorCode.PARAMS_ERROR);
    }

}