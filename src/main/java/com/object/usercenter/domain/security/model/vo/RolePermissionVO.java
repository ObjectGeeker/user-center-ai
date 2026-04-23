package com.object.usercenter.domain.security.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 角色权限对照关系VO,连表返回角色与权限的明细信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionVO extends BaseVO {

    /**
     * 角色id
     */
    @Schema(description = "角色id")
    private String roleId;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String roleName;

    /**
     * 角色key
     */
    @Schema(description = "角色key")
    private String roleKey;

    /**
     * 权限id
     */
    @Schema(description = "权限id")
    private String permissionId;

    /**
     * 权限名称
     */
    @Schema(description = "权限名称")
    private String permissionName;

    /**
     * 权限key
     */
    @Schema(description = "权限key")
    private String permissionKey;

}
