package com.object.usercenter.domain.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.object.usercenter.domain.security.repository.UserMapper;
import com.object.usercenter.domain.security.model.po.UserPO;
import com.object.usercenter.domain.user.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, UserPO> implements IUserService {
}
