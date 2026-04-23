package com.object.usercenter.domain.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {

    /**
     * 超级管理员
     */
    SUPER_ADMIN,
    /**
     * 管理员
     */
    ADMIN,
    /**
     * 普通用户
     */
    USER

}
