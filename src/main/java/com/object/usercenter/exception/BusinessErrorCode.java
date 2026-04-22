package com.object.usercenter.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessErrorCode {
    SUCCESS(200, "success"),
    PARAMS_ERROR(40000, "参数错误"),
    FORBIDDEN_ERROR(40300, "禁止操作"),
    NOT_FOUND_ERROR(40400, "数据不存在"),
    OPERATION_ERROR(50000, "操作失败");

    private final Integer code;
    private final String message;

}
