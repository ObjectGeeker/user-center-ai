package com.object.usercenter.domain.security.model.vo;

import com.object.usercenter.domain.security.enums.UserStatusEnum;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO extends BaseVO {
    /**
     * 用户账号(登录使用)
     */
    private String account;
    /**
     * 用户名
     */
    private String name;
    /**
     * 用户密码(登录使用)
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 账号状态 ACTIVE,BAN
     */
    private UserStatusEnum status;
    /**
     * 角色列表
     */
    private List<String> roles;
}
