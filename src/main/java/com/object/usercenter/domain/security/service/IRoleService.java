package com.object.usercenter.domain.security.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.object.usercenter.common.DataContainer;
import com.object.usercenter.common.PageRequest;
import com.object.usercenter.domain.security.model.po.RolePO;
import com.object.usercenter.domain.security.model.request.RoleQueryRequest;
import com.object.usercenter.domain.security.model.vo.RoleVO;

public interface IRoleService extends IService<RolePO> {

    /**
     * 角色分页查询
     *
     * @param pageRequest 分页请求体
     * @return 分页数据
     */
    IPage<RoleVO> findRoleList(PageRequest<RoleQueryRequest> pageRequest);

    /**
     * 角色批量保存
     *
     * @param dataContainer 数据容器
     */
    void doBatchSave(DataContainer<RoleVO> dataContainer);
}

