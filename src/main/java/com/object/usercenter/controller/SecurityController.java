package com.object.usercenter.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import com.object.usercenter.common.BaseResponse;
import com.object.usercenter.common.ResultUtil;
import com.object.usercenter.model.request.LoginRequest;
import com.object.usercenter.model.vo.DesensitizedUserVO;
import com.object.usercenter.service.ISecurityService;
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

    @PostMapping("test")
    @Operation(summary = "测试接口")
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    @SaCheckLogin
    public BaseResponse<Object> test() {
        return ResultUtil.ok("测试成功");
    }

}
