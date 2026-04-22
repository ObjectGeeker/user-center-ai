package com.object.usercenter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatusEnum {

    ACTIVE("活跃"),
    BAN("封禁");

    private final String desc;

    public static UserStatusEnum getByName(String name) {
        UserStatusEnum[] values = values();
        for (UserStatusEnum userStatusEnum : values) {
            if (userStatusEnum.name().equals(name)) {
                return userStatusEnum;
            }
        }
        return null;
    }
}
