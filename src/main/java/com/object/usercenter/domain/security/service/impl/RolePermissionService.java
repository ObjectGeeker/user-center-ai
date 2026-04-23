package com.object.usercenter.domain.security.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.object.usercenter.domain.security.enums.RoleEnum;
import com.object.usercenter.domain.security.model.po.RolePO;
import com.object.usercenter.domain.security.model.po.UserPO;
import com.object.usercenter.domain.security.model.request.RoleAllocateRequest;
import com.object.usercenter.domain.security.repository.RoleMapper;
import com.object.usercenter.domain.security.repository.RolePermissionMapper;
import com.object.usercenter.domain.security.model.po.RolePermissionPO;
import com.object.usercenter.domain.security.repository.UserMapper;
import com.object.usercenter.domain.security.service.IRolePermissionService;
import com.object.usercenter.exception.BusinessErrorCode;
import com.object.usercenter.exception.BusinessException;
import com.object.usercenter.utils.ThrowUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;

@Service
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermissionPO> implements IRolePermissionService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public void doAllocate(RoleAllocateRequest roleAllocateRequest) {
        //1. 管理员不可更改自己及超级管理员的权限
        validateAdmin(roleAllocateRequest);
        //2. 不可重复授权
        boolean exists = this.lambdaQuery().eq(RolePermissionPO::getRoleId, roleAllocateRequest.getRoleId()).eq(RolePermissionPO::getPermissionId, roleAllocateRequest.getPermissionId()).exists();
        ThrowUtil.throwIf(exists, BusinessErrorCode.PARAMS_ERROR, "不可重复授权");
        //3. 保存
        RolePermissionPO rolePermissionPO = new RolePermissionPO();
        rolePermissionPO.setRoleId(roleAllocateRequest.getRoleId());
        rolePermissionPO.setPermissionId(roleAllocateRequest.getPermissionId());
        this.save(rolePermissionPO);
    }

    private void validateAdmin(RoleAllocateRequest roleAllocateRequest) {
        String currentUserId = (String) StpUtil.getLoginId();
        UserPO userPO = userMapper.selectById(currentUserId);
        RolePO rolePO = roleMapper.selectById(roleAllocateRequest.getRoleId());

        List<String> roleList = JSONUtil.parseArray(userPO.getRoles()).toList(String.class);
        if ((RoleEnum.SUPER_ADMIN.name().equals(rolePO.getRoleKey()) || RoleEnum.ADMIN.name().equals(rolePO.getRoleKey())) && roleList.contains(RoleEnum.ADMIN.name())) {
            throw new BusinessException(BusinessErrorCode.PARAMS_ERROR, "管理员不可对超级管理员及管理员角色进行授权");
        }
    }

}

