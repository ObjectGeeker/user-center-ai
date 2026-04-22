package com.object.usercenter.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final Integer code;

    private final String message;

    public BusinessException(BusinessErrorCode businessErrorCode) {
        this.code = businessErrorCode.getCode();
        this.message = businessErrorCode.getMessage();
    }

    public BusinessException(BusinessErrorCode businessErrorCode, String message) {
        this.code = businessErrorCode.getCode();
        this.message = message;
    }

}
