package com.object.usercenter.domain.security.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "权限查询请求体")
public class PermissionQueryRequest {

    @Schema(description = "权限名称")
    private String name;

    @Schema(description = "权限描述")
    private String description;

    @Schema(description = "权限key")
    private String permissionKey;
}

