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
@Schema(description = "创建角色请求体")
public class RoleCreateRequest implements Serializable {

    @Schema(description = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    @Length(max = 50, message = "角色名称最多50位")
    private String name;

    @Schema(description = "角色描述")
    @Length(max = 255, message = "角色描述最多255位")
    private String description;

    @Schema(description = "角色key")
    @NotBlank(message = "角色key不能为空")
    @Length(max = 50, message = "角色key最多50位")
    private String roleKey;
}

