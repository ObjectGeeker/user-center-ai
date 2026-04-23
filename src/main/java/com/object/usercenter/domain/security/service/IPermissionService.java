package com.object.usercenter.domain.security.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.object.usercenter.common.DataContainer;
import com.object.usercenter.common.PageRequest;
import com.object.usercenter.domain.security.model.po.PermissionPO;
import com.object.usercenter.domain.security.model.request.PermissionQueryRequest;
import com.object.usercenter.domain.security.model.vo.PermissionVO;

public interface IPermissionService extends IService<PermissionPO> {

    /**
     * 权限分页查询
     *
     * @param pageRequest 分页请求体
     * @return 分页数据
     */
    IPage<PermissionVO> findPermissionList(PageRequest<PermissionQueryRequest> pageRequest);

    /**
     * 权限批量保存
     *
     * @param dataContainer 数据容器
     */
    void doBatchSave(DataContainer<PermissionVO> dataContainer);
}

