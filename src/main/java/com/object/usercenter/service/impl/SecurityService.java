package com.object.usercenter.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.object.usercenter.exception.BusinessErrorCode;
import com.object.usercenter.model.po.UserPO;
import com.object.usercenter.model.request.LoginRequest;
import com.object.usercenter.model.vo.DesensitizedUserVO;
import com.object.usercenter.service.ISecurityService;
import com.object.usercenter.service.IUserService;
import com.object.usercenter.utils.ThrowUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
