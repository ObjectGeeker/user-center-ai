package com.object.usercenter.domain.security.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.object.usercenter.domain.security.model.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserPO> {
}
