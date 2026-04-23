package com.object.usercenter.domain.security.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import com.object.usercenter.common.BaseResponse;
import com.object.usercenter.common.ResultUtil;
import com.object.usercenter.domain.security.model.request.LoginRequest;
import com.object.usercenter.domain.security.model.request.RegisterRequest;
import com.object.usercenter.domain.security.model.vo.DesensitizedUserVO;
import com.object.usercenter.domain.security.service.ISecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("security")
@Tag(name = "securityApi", description = "安全接口")
public class SecurityController {

    @Resource
    private ISecurityService securityService;

    @PostMapping("login")
    @Operation(summary = "登录")
    public BaseResponse<DesensitizedUserVO> login(@RequestBody @Validated LoginRequest loginRequest) {
        DesensitizedUserVO desensitizedUser = securityService.doLogin(loginRequest);
        return ResultUtil.ok(desensitizedUser);
    }

    @PostMapping("register")
    @Operation(summary = "注册")
    public BaseResponse<DesensitizedUserVO> register(@RequestBody @Validated RegisterRequest registerRequest) {
        DesensitizedUserVO desensitizedUser = securityService.doRegister(registerRequest);
        return ResultUtil.ok(desensitizedUser);
    }

}
