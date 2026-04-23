package com.object.usercenter.domain.security.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@TableName("tb_role")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolePO extends BasePO {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 角色key,与user表的roles列对应
     */
    private String roleKey;

}
