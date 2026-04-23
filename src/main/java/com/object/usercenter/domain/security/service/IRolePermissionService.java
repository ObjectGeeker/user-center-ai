package com.object.usercenter.domain.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.object.usercenter.domain.security.model.po.RolePermissionPO;
import com.object.usercenter.domain.security.model.request.RoleAllocateRequest;

public interface IRolePermissionService extends IService<RolePermissionPO> {
    /**
     * 分配权限给角色
     *
     * @param roleAllocateRequest 权限分配请求体
     */
    void doAllocate(RoleAllocateRequest roleAllocateRequest);
}

