package com.object.usercenter.domain.security.service;

import com.object.usercenter.domain.security.model.request.LoginRequest;
import com.object.usercenter.domain.security.model.request.RegisterRequest;
import com.object.usercenter.domain.security.model.vo.DesensitizedUserVO;

/**
 * 安全服务
 */
public interface ISecurityService {

    /**
     * 登录方法
     *
     * @param loginRequest 登录请求体
     * @return 脱敏用户信息
     */
    DesensitizedUserVO doLogin(LoginRequest loginRequest);

    /**
     * 注册方法
     *
     * @param registerRequest 注册请求体
     * @return 脱敏用户信息
     */
    DesensitizedUserVO doRegister(RegisterRequest registerRequest);
}
