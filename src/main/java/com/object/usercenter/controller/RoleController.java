package com.object.usercenter.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.bean.BeanUtil;
import com.object.usercenter.common.BaseResponse;
import com.object.usercenter.common.IdReq;
import com.object.usercenter.common.ResultUtil;
import com.object.usercenter.exception.BusinessErrorCode;
import com.object.usercenter.model.po.RolePO;
import com.object.usercenter.model.request.RoleCreateRequest;
import com.object.usercenter.model.request.RoleUpdateRequest;
import com.object.usercenter.model.vo.RoleVO;
import com.object.usercenter.service.IRoleService;
import com.object.usercenter.utils.ThrowUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("role")
@Tag(name = "roleApi", description = "角色管理接口")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @PostMapping("create")
    @Operation(summary = "创建角色")
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    @SaCheckLogin
    public BaseResponse<String> create(@RequestBody @Validated RoleCreateRequest request) {
        RolePO po = new RolePO();
        BeanUtil.copyProperties(request, po);
        boolean saved = roleService.save(po);
        ThrowUtil.throwIf(!saved, BusinessErrorCode.OPERATION_ERROR, "创建失败");
        return ResultUtil.ok(po.getId());
    }

    @PostMapping("update")
    @Operation(summary = "更新角色")
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    @SaCheckLogin
    public BaseResponse<Boolean> update(@RequestBody @Validated RoleUpdateRequest request) {
        RolePO exist = roleService.getById(request.getId());
        ThrowUtil.throwIf(exist == null, BusinessErrorCode.NOT_FOUND_ERROR, "角色不存在");
        BeanUtil.copyProperties(request, exist);
        boolean updated = roleService.updateById(exist);
        ThrowUtil.throwIf(!updated, BusinessErrorCode.OPERATION_ERROR, "更新失败");
        return ResultUtil.ok(true);
    }

    @PostMapping("delete")
    @Operation(summary = "删除角色(逻辑删除)")
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    @SaCheckLogin
    public BaseResponse<Boolean> delete(@RequestBody IdReq idReq) {
        ThrowUtil.throwIf(idReq == null || StrUtil.isBlank(idReq.getId()), BusinessErrorCode.PARAMS_ERROR, "id不能为空");
        RolePO exist = roleService.getById(idReq.getId());
        ThrowUtil.throwIf(exist == null, BusinessErrorCode.NOT_FOUND_ERROR, "角色不存在");
        boolean removed = roleService.removeById(idReq.getId());
        ThrowUtil.throwIf(!removed, BusinessErrorCode.OPERATION_ERROR, "删除失败");
        return ResultUtil.ok(true);
    }

    @PostMapping("get")
    @Operation(summary = "根据id查询角色")
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    @SaCheckLogin
    public BaseResponse<RoleVO> get(@RequestBody IdReq idReq) {
        ThrowUtil.throwIf(idReq == null || StrUtil.isBlank(idReq.getId()), BusinessErrorCode.PARAMS_ERROR, "id不能为空");
        RolePO exist = roleService.getById(idReq.getId());
        ThrowUtil.throwIf(exist == null, BusinessErrorCode.NOT_FOUND_ERROR, "角色不存在");
        RoleVO vo = new RoleVO();
        BeanUtil.copyProperties(exist, vo);
        return ResultUtil.ok(vo);
    }

    @GetMapping("list")
    @Operation(summary = "查询角色列表")
    @SaCheckRole(value = {"SUPER_ADMIN", "ADMIN"}, mode = SaMode.OR)
    @SaCheckLogin
    public BaseResponse<List<RoleVO>> list() {
        List<RolePO> list = roleService.lambdaQuery()
                .orderByAsc(RolePO::getCreateTime)
                .list();
        List<RoleVO> vos = list.stream().map(po -> {
            RoleVO vo = new RoleVO();
            BeanUtil.copyProperties(po, vo);
            return vo;
        }).toList();
        return ResultUtil.ok(vos);
    }
}

