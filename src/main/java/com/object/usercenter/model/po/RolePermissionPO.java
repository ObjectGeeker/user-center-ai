package com.object.usercenter.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@TableName("tb_role_permission")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionPO extends BasePO {

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 权限id
     */
    private String permissionId;

}
