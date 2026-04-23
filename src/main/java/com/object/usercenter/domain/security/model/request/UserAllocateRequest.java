package com.object.usercenter.domain.security.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UserAllocateRequest {

    @Schema(description = "用户id")
    @NotBlank(message = "用户id不能为空")
    private String userId;

    @Schema(description = "角色key")
    @NotBlank(message = "角色key不能为空")
    private String roleKey;
}

