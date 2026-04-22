package com.object.usercenter.utils;

import com.object.usercenter.exception.BusinessErrorCode;
import com.object.usercenter.exception.BusinessException;

public class ThrowUtil {

    public static void throwIf(boolean condition, BusinessErrorCode errorCode, String message) {
        if (condition) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void throwIf(boolean condition, BusinessErrorCode errorCode) {
        if (condition) {
            throw new BusinessException(errorCode);
        }
    }

}
