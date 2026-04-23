package com.object.usercenter.domain.security.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.bean.BeanUtil;
import com.object.usercenter.common.BaseResponse;
import com.object.usercenter.common.IdReq;
import com.object.usercenter.common.ResultUtil;
import com.object.usercenter.exception.BusinessErrorCode;
import com.object.usercenter.domain.security.model.po.PermissionPO;
import com.object.usercenter.domain.security.model.request.PermissionCreateRequest;
import com.object.usercenter.domain.security.model.request.PermissionUpdateRequest;
import com.object.usercenter.domain.security.model.vo.PermissionVO;
import com.object.usercenter.domain.security.service.IPermissionService;
import com.object.usercenter.utils.ThrowUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("permission")
@Tag(name = "permissionApi", description = "权限管理接口")
public class PermissionController {

    @Resource
    private IPermissionService permissionService;

    @PostMapping("create")
    @Operation(summary = "创建权限")
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    @SaCheckLogin
    public BaseResponse<String> create(@RequestBody @Validated PermissionCreateRequest request) {
        PermissionPO po = new PermissionPO();
        BeanUtil.copyProperties(request, po);
        boolean saved = permissionService.save(po);
        ThrowUtil.throwIf(!saved, BusinessErrorCode.OPERATION_ERROR, "创建失败");
        return ResultUtil.ok(po.getId());
    }

    @PostMapping("update")
    @Operation(summary = "更新权限")
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    @SaCheckLogin
    public BaseResponse<Boolean> update(@RequestBody @Validated PermissionUpdateRequest request) {
        PermissionPO exist = permissionService.getById(request.getId());
        ThrowUtil.throwIf(exist == null, BusinessErrorCode.NOT_FOUND_ERROR, "权限不存在");
        BeanUtil.copyProperties(request, exist);
        boolean updated = permissionService.updateById(exist);
        ThrowUtil.throwIf(!updated, BusinessErrorCode.OPERATION_ERROR, "更新失败");
        return ResultUtil.ok(true);
    }

    @PostMapping("delete")
    @Operation(summary = "删除权限(逻辑删除)")
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    @SaCheckLogin
    public BaseResponse<Boolean> delete(@RequestBody IdReq idReq) {
        ThrowUtil.throwIf(idReq == null || StrUtil.isBlank(idReq.getId()), BusinessErrorCode.PARAMS_ERROR, "id不能为空");
        PermissionPO exist = permissionService.getById(idReq.getId());
        ThrowUtil.throwIf(exist == null, BusinessErrorCode.NOT_FOUND_ERROR, "权限不存在");
        boolean removed = permissionService.removeById(idReq.getId());
        ThrowUtil.throwIf(!removed, BusinessErrorCode.OPERATION_ERROR, "删除失败");
        return ResultUtil.ok(true);
    }

    @PostMapping("get")
    @Operation(summary = "根据id查询权限")
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    @SaCheckLogin
    public BaseResponse<PermissionVO> get(@RequestBody IdReq idReq) {
        ThrowUtil.throwIf(idReq == null || StrUtil.isBlank(idReq.getId()), BusinessErrorCode.PARAMS_ERROR, "id不能为空");
        PermissionPO exist = permissionService.getById(idReq.getId());
        ThrowUtil.throwIf(exist == null, BusinessErrorCode.NOT_FOUND_ERROR, "权限不存在");
        PermissionVO vo = new PermissionVO();
        BeanUtil.copyProperties(exist, vo);
        return ResultUtil.ok(vo);
    }

    @GetMapping("list")
    @Operation(summary = "查询权限列表")
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    @SaCheckLogin
    public BaseResponse<List<PermissionVO>> list() {
        List<PermissionPO> list = permissionService.lambdaQuery()
                .orderByAsc(PermissionPO::getCreateTime)
                .list();
        List<PermissionVO> vos = list.stream().map(po -> {
            PermissionVO vo = new PermissionVO();
            BeanUtil.copyProperties(po, vo);
            return vo;
        }).toList();
        return ResultUtil.ok(vos);
    }
}

