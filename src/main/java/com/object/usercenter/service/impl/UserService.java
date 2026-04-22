package com.object.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.object.usercenter.mapper.UserMapper;
import com.object.usercenter.model.po.UserPO;
import com.object.usercenter.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, UserPO> implements IUserService {
}
