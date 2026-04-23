package com.object.usercenter.domain.security.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.object.usercenter.common.BaseResponse;
import com.object.usercenter.common.DataContainer;
import com.object.usercenter.common.PageRequest;
import com.object.usercenter.common.ResultUtil;
import com.object.usercenter.domain.security.enums.RoleEnum;
import com.object.usercenter.domain.security.model.request.*;
import com.object.usercenter.domain.security.model.vo.DesensitizedUserVO;
import com.object.usercenter.domain.security.model.vo.PermissionVO;
import com.object.usercenter.domain.security.model.vo.RoleVO;
import com.object.usercenter.domain.security.service.IPermissionService;
import com.object.usercenter.domain.security.service.IRolePermissionService;
import com.object.usercenter.domain.security.service.IRoleService;
import com.object.usercenter.domain.security.service.ISecurityService;
import com.object.usercenter.domain.user.service.IUserService;
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
@Tag(name = "securityApi", description = "安全权限接口")
public class SecurityController {

    @Resource
    private ISecurityService securityService;

    @Resource
    private IRoleService roleService;

    @Resource
    private IPermissionService permissionService;

    @Resource
    private IRolePermissionService rolePermissionService;

    @Resource
    private IUserService userService;

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

    @PostMapping("role/findList")
    @Operation(summary = "角色列表查询")
    @SaCheckLogin
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    public BaseResponse<IPage<RoleVO>> findRoleList(@RequestBody PageRequest<RoleQueryRequest> pageRequest) {
        IPage<RoleVO> pageResult = roleService.findRoleList(pageRequest);
        return ResultUtil.ok(pageResult);
    }

    @PostMapping("permission/findList")
    @Operation(summary = "权限列表查询")
    @SaCheckLogin
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    public BaseResponse<IPage<PermissionVO>> findPermissionList(@RequestBody PageRequest<PermissionQueryRequest> pageRequest) {
        IPage<PermissionVO> pageResult = permissionService.findPermissionList(pageRequest);
        return ResultUtil.ok(pageResult);
    }

    @PostMapping("role/batchSave")
    @Operation(summary = "角色批量保存")
    @SaCheckLogin
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    public BaseResponse<Object> roleBatchSave(@RequestBody @Validated DataContainer<RoleVO> dataContainer) {
        roleService.doBatchSave(dataContainer);
        return ResultUtil.ok("success");
    }

    @PostMapping("permission/batchSave")
    @Operation(summary = "权限批量保存")
    @SaCheckLogin
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    public BaseResponse<Object> permissionBatchSave(@RequestBody @Validated DataContainer<PermissionVO> dataContainer) {
        permissionService.doBatchSave(dataContainer);
        return ResultUtil.ok("success");
    }

    @PostMapping("role/allocate")
    @Operation(summary = "分配权限给角色")
    @SaCheckLogin
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    public BaseResponse<Object> roleAllocate(@RequestBody RoleAllocateRequest roleAllocateRequest) {
        rolePermissionService.doAllocate(roleAllocateRequest);
        return ResultUtil.ok("success");
    }

    @PostMapping("allocate")
    @Operation(summary = "分配角色给用户")
    @SaCheckLogin
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    public BaseResponse<Object> allocate(@RequestBody @Validated UserAllocateRequest userAllocateRequest) {
        userService.doAllocate(userAllocateRequest);
        return ResultUtil.ok("success");
    }

}
