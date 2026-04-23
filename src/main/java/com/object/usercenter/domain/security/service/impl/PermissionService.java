package com.object.usercenter.domain.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.object.usercenter.domain.security.repository.PermissionMapper;
import com.object.usercenter.domain.security.model.po.PermissionPO;
import com.object.usercenter.domain.security.service.IPermissionService;
import org.springframework.stereotype.Service;

@Service
public class PermissionService extends ServiceImpl<PermissionMapper, PermissionPO> implements IPermissionService {
}

