package com.object.usercenter.domain.security.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.object.usercenter.common.DataContainer;
import com.object.usercenter.common.PageRequest;
import com.object.usercenter.domain.security.model.po.RolePermissionPO;
import com.object.usercenter.domain.security.repository.PermissionMapper;
import com.object.usercenter.domain.security.model.po.PermissionPO;
import com.object.usercenter.domain.security.model.request.PermissionQueryRequest;
import com.object.usercenter.domain.security.model.vo.PermissionVO;
import com.object.usercenter.domain.security.service.IPermissionService;
import com.object.usercenter.exception.BusinessErrorCode;
import com.object.usercenter.utils.ThrowUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService extends ServiceImpl<PermissionMapper, PermissionPO> implements IPermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RolePermissionService rolePermissionService;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public IPage<PermissionVO> findPermissionList(PageRequest<PermissionQueryRequest> pageRequest) {
        PermissionQueryRequest filterInfo = pageRequest.getFilterInfo();
        return permissionMapper.selectAllPermissions(new Page<>(pageRequest.getPageIndex(), pageRequest.getPageSize()), filterInfo);
    }

    @Override
    public void doBatchSave(DataContainer<PermissionVO> dataContainer) {
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

    private void doBatchCreate(List<PermissionVO> addedData) {
        validateDuplicateKey(addedData);

        List<PermissionPO> saveValues = addedData.stream().map(permissionVO -> {
            PermissionPO permissionPO = new PermissionPO();
            BeanUtil.copyProperties(permissionVO, permissionPO, "id");
            return permissionPO;
        }).collect(Collectors.toList());

        this.saveBatch(saveValues);
    }

    private void validateDuplicateKey(List<PermissionVO> addedData) {
        List<String> permissionKeys = addedData.stream().map(PermissionVO::getPermissionKey).collect(Collectors.toList());
        LambdaQueryWrapper<PermissionPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(PermissionPO::getPermissionKey, permissionKeys);
        boolean exists = this.exists(wrapper);
        ThrowUtil.throwIf(exists, BusinessErrorCode.PARAMS_ERROR, "权限Key不能重复");
    }

    private void doBatchUpdate(List<PermissionVO> modifiedData) {
        List<String> permissionKeys = modifiedData.stream().map(PermissionVO::getPermissionKey).collect(Collectors.toList());
        List<String> permissionIds = modifiedData.stream().map(PermissionVO::getId).collect(Collectors.toList());
        LambdaQueryWrapper<PermissionPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(PermissionPO::getPermissionKey, permissionKeys).notIn(PermissionPO::getId, permissionIds);
        boolean exists = this.exists(wrapper);
        ThrowUtil.throwIf(exists, BusinessErrorCode.PARAMS_ERROR, "权限Key不能重复");

        List<PermissionPO> saveValues = modifiedData.stream().map(permissionVO -> {
            PermissionPO permissionPO = new PermissionPO();
            BeanUtil.copyProperties(permissionVO, permissionPO, "id");
            return permissionPO;
        }).collect(Collectors.toList());

        this.saveOrUpdateBatch(saveValues);
    }

    private void doBatchDelete(List<PermissionVO> removedData) {
        List<String> permissionIds = removedData.stream()
                .map(PermissionVO::getId)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toList());

        transactionTemplate.execute((status) -> {
            LambdaQueryWrapper<RolePermissionPO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(RolePermissionPO::getPermissionId, permissionIds);
            rolePermissionService.remove(queryWrapper);

            this.removeByIds(permissionIds);
            return 1;
        });
    }
}

