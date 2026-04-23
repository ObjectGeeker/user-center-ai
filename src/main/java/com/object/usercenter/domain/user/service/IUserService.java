package com.object.usercenter.domain.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.object.usercenter.domain.security.model.request.UserAllocateRequest;
import com.object.usercenter.domain.security.model.po.UserPO;

public interface IUserService extends IService<UserPO> {

    /**
     * 分配角色给用户（追加角色）
     *
     * @param userAllocateRequest 角色分配请求体
     */
    void doAllocate(UserAllocateRequest userAllocateRequest);
}
