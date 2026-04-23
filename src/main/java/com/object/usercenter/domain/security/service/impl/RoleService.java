package com.object.usercenter.domain.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.object.usercenter.domain.security.repository.RoleMapper;
import com.object.usercenter.domain.security.model.po.RolePO;
import com.object.usercenter.domain.security.service.IRoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends ServiceImpl<RoleMapper, RolePO> implements IRoleService {
}

