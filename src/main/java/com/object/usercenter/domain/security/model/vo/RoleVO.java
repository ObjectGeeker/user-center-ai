package com.object.usercenter.domain.security.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleVO extends BaseVO {

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String name;

    /**
     * 角色描述
     */
    @Schema(description = "角色描述")
    private String description;

    /**
     * 角色key,与user表的roles列对应
     */
    @Schema(description = "角色key")
    private String roleKey;

    /**
     * 该角色拥有的权限列表(连表查询填充)
     */
    @Schema(description = "该角色拥有的权限列表")
    private List<PermissionVO> permissions;

}
