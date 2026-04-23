package com.object.usercenter.domain.security.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.object.usercenter.domain.security.enums.RoleEnum;
import com.object.usercenter.domain.security.enums.UserStatusEnum;
import com.object.usercenter.domain.security.model.request.RegisterRequest;
import com.object.usercenter.exception.BusinessErrorCode;
import com.object.usercenter.domain.security.model.po.UserPO;
import com.object.usercenter.domain.security.model.request.LoginRequest;
import com.object.usercenter.domain.security.model.vo.DesensitizedUserVO;
import com.object.usercenter.domain.security.service.ISecurityService;
import com.object.usercenter.domain.user.service.IUserService;
import com.object.usercenter.utils.ThrowUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SecurityService implements ISecurityService {

    @Resource
    protected IUserService userService;

    @Override
    public DesensitizedUserVO doLogin(LoginRequest loginRequest) {
        //1. 根据账号查询用户数据
        UserPO existUser = userService.lambdaQuery().eq(UserPO::getAccount, loginRequest.getAccount()).one();
        ThrowUtil.throwIf(existUser == null, BusinessErrorCode.PARAMS_ERROR, "该账号不存在");
        //2. 校验密码
        String credentialMd5 = SaSecureUtil.md5(loginRequest.getCredential());
        ThrowUtil.throwIf(!existUser.getPassword().equals(credentialMd5), BusinessErrorCode.PARAMS_ERROR, "密码不正确");
        //3. 登录完成
        StpUtil.login(existUser.getId());
        //4. 转换脱敏后用户信息进行返回
        DesensitizedUserVO desensitizedUserVO = new DesensitizedUserVO();
        BeanUtil.copyProperties(existUser, desensitizedUserVO);
        return desensitizedUserVO;
    }

    @Override
    public DesensitizedUserVO doRegister(RegisterRequest registerRequest) {
        //1. 根据账号查询是否存在相同账号
        boolean exists = userService.lambdaQuery().eq(UserPO::getAccount, registerRequest.getAccount()).exists();
        ThrowUtil.throwIf(exists, BusinessErrorCode.PARAMS_ERROR, "该账号已存在");
        //2. 校验两次密码是否一致
        ThrowUtil.throwIf(!registerRequest.getCredential().equals(registerRequest.getCheckCredential()), BusinessErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        //3. 创建用户信息对象 (默认普通用户角色)
        UserPO userPO = new UserPO();
        userPO.setAccount(registerRequest.getAccount());
        userPO.setName(registerRequest.getAccount());
        userPO.setPassword(SaSecureUtil.md5(registerRequest.getCredential()));
        userPO.setStatus(UserStatusEnum.ACTIVE.name());
        userPO.setRoles(JSONUtil.toJsonStr(CollUtil.newArrayList(RoleEnum.USER.name())));
        //4. 保存用户信息
        userService.save(userPO);
        //5. 保存登录信息
        StpUtil.login(userPO.getId());
        //6. 转换脱敏后用户信息进行返回
        DesensitizedUserVO desensitizedUserVO = new DesensitizedUserVO();
        BeanUtil.copyProperties(userPO, desensitizedUserVO);
        return desensitizedUserVO;
    }
}
