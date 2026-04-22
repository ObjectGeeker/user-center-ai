package com.object.usercenter.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@TableName("tb_permission")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionPO extends BasePO {

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 权限key,适配sa-token权限认证格式
     */
    private String permissionKey;

}
