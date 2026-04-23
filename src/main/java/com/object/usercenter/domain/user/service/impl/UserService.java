package com.object.usercenter.domain.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.object.usercenter.domain.security.enums.RoleEnum;
import com.object.usercenter.domain.security.repository.UserMapper;
import com.object.usercenter.domain.security.model.po.UserPO;
import com.object.usercenter.domain.security.model.vo.RoleVO;
import com.object.usercenter.domain.security.model.request.UserAllocateRequest;
import com.object.usercenter.domain.security.repository.RoleMapper;
import com.object.usercenter.domain.user.service.IUserService;
import com.object.usercenter.exception.BusinessErrorCode;
import com.object.usercenter.utils.ThrowUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class UserService extends ServiceImpl<UserMapper, UserPO> implements IUserService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public void doAllocate(UserAllocateRequest userAllocateRequest) {
        UserPO exist = this.getById(userAllocateRequest.getUserId());
        ThrowUtil.throwIf(exist == null, BusinessErrorCode.NOT_FOUND_ERROR, "用户不存在");

        // 角色key必须存在
        RoleVO role = roleMapper.selectRoleWithPermissionsByKey(userAllocateRequest.getRoleKey());
        ThrowUtil.throwIf(role == null, BusinessErrorCode.NOT_FOUND_ERROR, "角色不存在");

        // 管理员不可把管理员角色分配给用户，只能由超级管理员分配管理员角色
        if (RoleEnum.ADMIN.name().equals(userAllocateRequest.getRoleKey())) {
            String currentUserId = (String) StpUtil.getLoginId();
            UserPO currentUser = this.getById(currentUserId);
            ThrowUtil.throwIf(currentUser == null, BusinessErrorCode.FORBIDDEN_ERROR, "无权限操作");
            List<String> currentRoles = new ArrayList<>();
            if (StrUtil.isNotBlank(currentUser.getRoles())) {
                currentRoles = JSONUtil.parseArray(currentUser.getRoles()).toList(String.class);
            }
            boolean isSuperAdmin = CollUtil.isNotEmpty(currentRoles) && currentRoles.contains(RoleEnum.SUPER_ADMIN.name());
            ThrowUtil.throwIf(!isSuperAdmin, BusinessErrorCode.FORBIDDEN_ERROR, "只有超级管理员可以分配管理员角色");
        }

        // 解析 roles(JSON数组字符串)，并追加去重
        List<String> roles = new ArrayList<>();
        if (StrUtil.isNotBlank(exist.getRoles())) {
            roles = JSONUtil.parseArray(exist.getRoles()).toList(String.class);
        }
        if (CollUtil.isEmpty(roles)) {
            roles = new ArrayList<>();
        }

        // 兜底：不允许把超级管理员角色分配给别人（如确需可放开）
        ThrowUtil.throwIf(RoleEnum.SUPER_ADMIN.name().equals(userAllocateRequest.getRoleKey()),
                BusinessErrorCode.FORBIDDEN_ERROR, "不允许分配超级管理员角色");

        LinkedHashSet<String> set = new LinkedHashSet<>(roles);
        boolean added = set.add(userAllocateRequest.getRoleKey());
        ThrowUtil.throwIf(!added, BusinessErrorCode.PARAMS_ERROR, "不可重复分配角色");

        exist.setRoles(JSONUtil.toJsonStr(set));
        boolean updated = this.updateById(exist);
        ThrowUtil.throwIf(!updated, BusinessErrorCode.OPERATION_ERROR, "分配失败");
    }
}
