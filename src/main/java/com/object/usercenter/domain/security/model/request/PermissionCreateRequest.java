package com.object.usercenter.domain.security.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "创建权限请求体")
public class PermissionCreateRequest implements Serializable {

    @Schema(description = "权限名称")
    @NotBlank(message = "权限名称不能为空")
    @Length(max = 50, message = "权限名称最多50位")
    private String name;

    @Schema(description = "权限描述")
    @Length(max = 255, message = "权限描述最多255位")
    private String description;

    @Schema(description = "权限key")
    @NotBlank(message = "权限key不能为空")
    @Length(max = 50, message = "权限key最多50位")
    private String permissionKey;
}

