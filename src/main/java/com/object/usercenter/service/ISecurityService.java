package com.object.usercenter.service;

import com.object.usercenter.model.request.LoginRequest;
import com.object.usercenter.model.vo.DesensitizedUserVO;

public interface ISecurityService {
    DesensitizedUserVO doLogin(LoginRequest loginRequest);
}
