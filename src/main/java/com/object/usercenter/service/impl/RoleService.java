package com.object.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.object.usercenter.mapper.RoleMapper;
import com.object.usercenter.model.po.RolePO;
import com.object.usercenter.service.IRoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends ServiceImpl<RoleMapper, RolePO> implements IRoleService {
}

