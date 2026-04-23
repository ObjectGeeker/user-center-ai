package com.object.usercenter.domain.security.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 脱敏后的用户信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DesensitizedUserVO {

    /**
     * 用户账号(登录使用)
     */
    @Schema(description = "用户账号")
    private String account;
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String name;
    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;
    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
