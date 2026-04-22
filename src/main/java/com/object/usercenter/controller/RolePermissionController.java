package com.object.usercenter.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.object.usercenter.common.BaseResponse;
import com.object.usercenter.common.IdReq;
import com.object.usercenter.common.ResultUtil;
import com.object.usercenter.exception.BusinessErrorCode;
import com.object.usercenter.mapper.RolePermissionMapper;
import com.object.usercenter.model.po.RolePO;
import com.object.usercenter.model.po.RolePermissionPO;
import com.object.usercenter.model.request.RolePermissionCreateRequest;
import com.object.usercenter.model.request.RolePermissionUpdateRequest;
import com.object.usercenter.model.vo.RolePermissionVO;
import com.object.usercenter.service.IRolePermissionService;
import com.object.usercenter.service.IRoleService;
import com.object.usercenter.utils.ThrowUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("role-permission")
@Tag(name = "rolePermissionApi", description = "角色权限关系管理接口")
public class RolePermissionController {

    private static final String ROLE_KEY_ADMIN = "ADMIN";
    private static final String ROLE_KEY_SUPER_ADMIN = "SUPER_ADMIN";

    @Resource
    private IRolePermissionService rolePermissionService;

    @Resource
    private IRoleService roleService;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @PostMapping("create")
    @Operation(summary = "创建角色-权限关系")
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    @SaCheckLogin
    public BaseResponse<String> create(@RequestBody @Validated RolePermissionCreateRequest request) {
        checkAdminCannotMutateAdminRolePermissions(request.getRoleId());

        RolePermissionPO po = new RolePermissionPO();
        BeanUtil.copyProperties(request, po);
        boolean saved = rolePermissionService.save(po);
        ThrowUtil.throwIf(!saved, BusinessErrorCode.OPERATION_ERROR, "创建失败");
        return ResultUtil.ok(po.getId());
    }

    @PostMapping("update")
    @Operation(summary = "更新角色-权限关系")
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    @SaCheckLogin
    public BaseResponse<Boolean> update(@RequestBody @Validated RolePermissionUpdateRequest request) {
        RolePermissionPO exist = rolePermissionService.getById(request.getId());
        ThrowUtil.throwIf(exist == null, BusinessErrorCode.NOT_FOUND_ERROR, "关系不存在");

        // 只要涉及到管理员角色，且当前不是超级管理员，就禁止修改
        checkAdminCannotMutateAdminRolePermissions(exist.getRoleId());
        checkAdminCannotMutateAdminRolePermissions(request.getRoleId());

        BeanUtil.copyProperties(request, exist);
        boolean updated = rolePermissionService.updateById(exist);
        ThrowUtil.throwIf(!updated, BusinessErrorCode.OPERATION_ERROR, "更新失败");
        return ResultUtil.ok(true);
    }

    @PostMapping("delete")
    @Operation(summary = "删除角色-权限关系(逻辑删除)")
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    @SaCheckLogin
    public BaseResponse<Boolean> delete(@RequestBody IdReq idReq) {
        ThrowUtil.throwIf(idReq == null || StrUtil.isBlank(idReq.getId()), BusinessErrorCode.PARAMS_ERROR, "id不能为空");
        RolePermissionPO exist = rolePermissionService.getById(idReq.getId());
        ThrowUtil.throwIf(exist == null, BusinessErrorCode.NOT_FOUND_ERROR, "关系不存在");
        checkAdminCannotMutateAdminRolePermissions(exist.getRoleId());

        boolean removed = rolePermissionService.removeById(idReq.getId());
        ThrowUtil.throwIf(!removed, BusinessErrorCode.OPERATION_ERROR, "删除失败");
        return ResultUtil.ok(true);
    }

    @PostMapping("get")
    @Operation(summary = "根据id查询角色-权限关系")
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    @SaCheckLogin
    public BaseResponse<RolePermissionVO> get(@RequestBody IdReq idReq) {
        ThrowUtil.throwIf(idReq == null || StrUtil.isBlank(idReq.getId()), BusinessErrorCode.PARAMS_ERROR, "id不能为空");
        RolePermissionPO exist = rolePermissionService.getById(idReq.getId());
        ThrowUtil.throwIf(exist == null, BusinessErrorCode.NOT_FOUND_ERROR, "关系不存在");
        RolePermissionVO vo = new RolePermissionVO();
        BeanUtil.copyProperties(exist, vo);
        return ResultUtil.ok(vo);
    }

    @GetMapping("list")
    @Operation(summary = "查询角色-权限关系明细列表(连表)")
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    @SaCheckLogin
    public BaseResponse<List<RolePermissionVO>> list() {
        List<RolePermissionVO> list = rolePermissionMapper.selectAllRolePermissionDetails();
        return ResultUtil.ok(list);
    }

    @PostMapping("list/by-role")
    @Operation(summary = "根据角色id查询该角色的权限关系明细(连表)")
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    @SaCheckLogin
    public BaseResponse<List<RolePermissionVO>> listByRole(@RequestBody IdReq idReq) {
        ThrowUtil.throwIf(idReq == null || StrUtil.isBlank(idReq.getId()), BusinessErrorCode.PARAMS_ERROR, "id不能为空");
        List<RolePermissionVO> list = rolePermissionMapper.selectRolePermissionDetailsByRoleId(idReq.getId());
        return ResultUtil.ok(list);
    }

    @PostMapping("list/by-permission")
    @Operation(summary = "根据权限id查询拥有该权限的角色明细(连表)")
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    @SaCheckLogin
    public BaseResponse<List<RolePermissionVO>> listByPermission(@RequestBody IdReq idReq) {
        ThrowUtil.throwIf(idReq == null || StrUtil.isBlank(idReq.getId()), BusinessErrorCode.PARAMS_ERROR, "id不能为空");
        List<RolePermissionVO> list = rolePermissionMapper.selectRolePermissionDetailsByPermissionId(idReq.getId());
        return ResultUtil.ok(list);
    }

    /**
     * 管理员(非超级管理员)不可对管理员角色权限做增删改
     */
    private void checkAdminCannotMutateAdminRolePermissions(String roleId) {
        if (!StpUtil.hasRole(ROLE_KEY_ADMIN) || StpUtil.hasRole(ROLE_KEY_SUPER_ADMIN)) {
            return;
        }
        RolePO role = roleService.getById(roleId);
        ThrowUtil.throwIf(role == null, BusinessErrorCode.NOT_FOUND_ERROR, "角色不存在");
        boolean isAdminRole = Objects.equals(role.getRoleKey(), ROLE_KEY_ADMIN) || Objects.equals(role.getRoleKey(), ROLE_KEY_SUPER_ADMIN);
        ThrowUtil.throwIf(isAdminRole, BusinessErrorCode.FORBIDDEN_ERROR, "管理员不可对管理员角色的权限进行增删改");
    }
}

