package com.object.usercenter.domain.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.object.usercenter.domain.security.repository.RolePermissionMapper;
import com.object.usercenter.domain.security.model.po.RolePermissionPO;
import com.object.usercenter.domain.security.service.IRolePermissionService;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermissionPO> implements IRolePermissionService {
}

