package com.object.usercenter.common;

import com.object.usercenter.exception.BusinessErrorCode;

public class ResultUtil {

    public static <T> BaseResponse<T> ok(T data) {

        return new BaseResponse<>(BusinessErrorCode.SUCCESS.getCode(), BusinessErrorCode.SUCCESS.getMessage(), data);
    }

    public static <T> BaseResponse<T> error(Integer code, String message) {
        return new BaseResponse<>(code, message);
    }

}
