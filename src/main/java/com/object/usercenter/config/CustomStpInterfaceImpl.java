package com.object.usercenter.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.object.usercenter.mapper.RoleMapper;
import com.object.usercenter.model.po.UserPO;
import com.object.usercenter.model.vo.PermissionVO;
import com.object.usercenter.model.vo.RoleVO;
import com.object.usercenter.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomStpInterfaceImpl implements StpInterface {

    @Resource
    private IUserService userService;
    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<String> getPermissionList(Object userId, String loginType) {
        UserPO existUser = userService.getById((String) userId);
        List<String> roles = JSONUtil.parseArray(existUser.getRoles()).toList(String.class);
        if (CollUtil.isEmpty(roles)) {
            return new ArrayList<>();
        }
        List<RoleVO> existRoles = roleMapper.selectRolesWithPermissionsByNames(roles);
        if (CollUtil.isEmpty(existRoles)) {
            return new ArrayList<>();
        }
        return existRoles.stream()
                .map(RoleVO::getPermissions)
                .filter(CollUtil::isNotEmpty)
                .flatMap(List::stream)
                .map(PermissionVO::getPermissionKey)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getRoleList(Object userId, String loginType) {
        UserPO existUser = userService.getById((String) userId);
        return JSONUtil.parseArray(existUser.getRoles()).toList(String.class);
    }
}
