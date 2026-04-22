package com.object.usercenter.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.hutool.core.util.StrUtil;
import com.object.usercenter.common.BaseResponse;
import com.object.usercenter.common.ResultUtil;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<Object> businessExceptionHandler(BusinessException e) {
        return ResultUtil.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    public BaseResponse<Object> notLoginExceptionHandler(NotLoginException e) {
        return ResultUtil.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NotRoleException.class)
    public BaseResponse<Object> notRoleExceptionHandler(NotRoleException e) {
        return ResultUtil.error(e.getCode(), "无权限访问服务");
    }

    @ExceptionHandler(NotPermissionException.class)
    public BaseResponse<Object> notPermissionExceptionHandler(NotPermissionException e) {
        return ResultUtil.error(e.getCode(), "无权限访问服务");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String errMsg = e.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).filter(StrUtil::isNotBlank).collect(Collectors.joining(","));
        return ResultUtil.error(BusinessErrorCode.PARAMS_ERROR.getCode(), errMsg);
    }

}
