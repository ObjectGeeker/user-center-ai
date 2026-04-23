package com.object.usercenter.domain.security.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.object.usercenter.common.DataContainer;
import com.object.usercenter.common.PageRequest;
import com.object.usercenter.domain.security.model.po.RolePO;
import com.object.usercenter.domain.security.model.po.RolePermissionPO;
import com.object.usercenter.domain.security.model.po.UserPO;
import com.object.usercenter.domain.security.model.request.RoleQueryRequest;
import com.object.usercenter.domain.security.model.vo.RoleVO;
import com.object.usercenter.domain.security.repository.RoleMapper;
import com.object.usercenter.domain.security.repository.UserMapper;
import com.object.usercenter.domain.security.service.IRoleService;
import com.object.usercenter.exception.BusinessErrorCode;
import com.object.usercenter.utils.ThrowUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService extends ServiceImpl<RoleMapper, RolePO> implements IRoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private RolePermissionService rolePermissionService;

    @Override
    public IPage<RoleVO> findRoleList(PageRequest<RoleQueryRequest> pageRequest) {
        RoleQueryRequest filterInfo = pageRequest.getFilterInfo();
        return roleMapper.selectAllRolesWithPermissions(new Page<>(pageRequest.getPageIndex(), pageRequest.getPageSize()), filterInfo);
    }

    @Override
    public void doBatchSave(DataContainer<RoleVO> dataContainer) {
        if (CollUtil.isNotEmpty(dataContainer.getAddedData())) {
            doBatchCreate(dataContainer.getAddedData());
        }
        if (CollUtil.isNotEmpty(dataContainer.getModifiedData())) {
            doBatchUpdate(dataContainer.getModifiedData());
        }
        if (CollUtil.isNotEmpty(dataContainer.getRemovedData())) {
            doBatchDelete(dataContainer.getRemovedData());
        }
    }

    private void doBatchUpdate(List<RoleVO> modifiedData) {
        List<String> roleKeys = modifiedData.stream().map(RoleVO::getRoleKey).collect(Collectors.toList());
        List<String> roleIds = modifiedData.stream().map(RoleVO::getId).collect(Collectors.toList());
        LambdaQueryWrapper<RolePO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(RolePO::getRoleKey, roleKeys).notIn(RolePO::getId, roleIds);
        boolean exists = this.exists(wrapper);
        ThrowUtil.throwIf(exists, BusinessErrorCode.PARAMS_ERROR, "角色Key不能重复");

        // 判断被修改的角色有没有被引用过，如果被用户使用了，就不能修改了
        validateRoleKeys(roleIds);

        List<RolePO> saveValues = modifiedData.stream().map(roleVO -> {
            RolePO rolePO = new RolePO();
            BeanUtil.copyProperties(roleVO, rolePO, "id");
            return rolePO;
        }).collect(Collectors.toList());

        this.saveOrUpdateBatch(saveValues);
    }

    private void validateRoleKeys(List<String> roleIds) {
        List<RolePO> rolePOS = this.listByIds(roleIds);
        List<String> existRoleKeys = rolePOS.stream().map(RolePO::getRoleKey).collect(Collectors.toList());
        LambdaQueryWrapper<UserPO> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.apply("JSON_OVERLAPS(roles, {0})", existRoleKeys);
        boolean exists = userMapper.exists(userWrapper);
        ThrowUtil.throwIf(exists, BusinessErrorCode.PARAMS_ERROR, "角色key已经分配给用户，无法删除");
    }

    private void doBatchCreate(List<RoleVO> addedData) {
        List<String> roleKeys = addedData.stream().map(RoleVO::getRoleKey).collect(Collectors.toList());
        LambdaQueryWrapper<RolePO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(RolePO::getRoleKey, roleKeys);
        boolean exists = this.exists(wrapper);
        ThrowUtil.throwIf(exists, BusinessErrorCode.PARAMS_ERROR, "角色Key不能重复");

        //转换并插入数据
        List<RolePO> saveValues = addedData.stream().map(roleVO -> {
            RolePO rolePO = new RolePO();
            BeanUtil.copyProperties(roleVO, rolePO, "id");
            return rolePO;
        }).collect(Collectors.toList());

        this.saveBatch(saveValues);
    }

    private void doBatchDelete(List<RoleVO> removedData) {
        List<String> roleIds = removedData.stream().map(RoleVO::getId).filter(StrUtil::isNotBlank).collect(Collectors.toList());
        // 查询角色是否被用户使用
        validateRoleKeys(roleIds);
        transactionTemplate.execute((status) -> {
            // 先删除权限角色表的数据
            LambdaQueryWrapper<RolePermissionPO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(RolePermissionPO::getRoleId, roleIds);
            rolePermissionService.remove(queryWrapper);

            // 再删除角色表数据
            this.removeByIds(roleIds);
            return 1;
        });
    }
}

