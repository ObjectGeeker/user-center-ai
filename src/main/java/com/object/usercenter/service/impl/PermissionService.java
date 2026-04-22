package com.object.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.object.usercenter.mapper.PermissionMapper;
import com.object.usercenter.model.po.PermissionPO;
import com.object.usercenter.service.IPermissionService;
import org.springframework.stereotype.Service;

@Service
public class PermissionService extends ServiceImpl<PermissionMapper, PermissionPO> implements IPermissionService {
}

