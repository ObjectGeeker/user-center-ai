package com.object.usercenter.domain.security.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    /**
     * 账号
     */
    @Schema(description = "账号")
    @NotNull(message = "账号不可为空")
    @Length(max = 15, message = "账号最多15位")
    private String account;
    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;
    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 注册凭证(密码,验证码等)
     */
    @Schema(description = "注册凭证")
    @NotNull(message = "注册凭证不能为空")
    @Length(max = 20, message = "注册凭证不能超过20位")
    private String credential;

    /**
     * 确认注册凭证(密码,验证码等)
     */
    @Schema(description = "确认注册凭证")
    @NotNull(message = "确认注册凭证不能为空")
    @Length(max = 20, message = "确认注册凭证不能超过20位")
    private String checkCredential;

}
