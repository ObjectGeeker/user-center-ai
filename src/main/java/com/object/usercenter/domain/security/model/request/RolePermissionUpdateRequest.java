package com.object.usercenter.domain.security.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "更新角色-权限关系请求体")
public class RolePermissionUpdateRequest implements Serializable {

    @Schema(description = "关系id")
    @NotBlank(message = "关系id不能为空")
    private String id;

    @Schema(description = "角色id")
    @NotBlank(message = "角色id不能为空")
    private String roleId;

    @Schema(description = "权限id")
    @NotBlank(message = "权限id不能为空")
    private String permissionId;
}

