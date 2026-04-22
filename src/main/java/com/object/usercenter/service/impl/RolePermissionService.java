package com.object.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.object.usercenter.mapper.RolePermissionMapper;
import com.object.usercenter.model.po.RolePermissionPO;
import com.object.usercenter.service.IRolePermissionService;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermissionPO> implements IRolePermissionService {
}

