package com.object.usercenter.domain.security.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@TableName("tb_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPO extends BasePO {

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
    private String status;
    /**
     * 角色列表
     */
    private String roles;

}
