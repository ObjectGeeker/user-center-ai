package com.object.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.object.usercenter.model.po.RolePO;
import com.object.usercenter.model.vo.RoleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<RolePO> {

    /**
     * 根据角色id查询角色详情并携带其所有权限(连表 tb_role + tb_role_permission + tb_permission)
     *
     * @param roleId 角色id
     * @return 角色及其权限信息
     */
    RoleVO selectRoleWithPermissionsById(@Param("roleId") String roleId);

    /**
     * 根据角色key查询角色详情并携带其所有权限(连表 tb_role + tb_role_permission + tb_permission)
     *
     * @param roleKey 角色key
     * @return 角色及其权限信息
     */
    RoleVO selectRoleWithPermissionsByKey(@Param("roleKey") String roleKey);

    /**
     * 查询所有角色并携带它们各自的权限列表(连表 tb_role + tb_role_permission + tb_permission)
     *
     * @return 角色列表(含权限)
     */
    List<RoleVO> selectAllRolesWithPermissions();

    /**
     * 根据角色名称查询角色详情并携带其所有权限(连表 tb_role + tb_role_permission + tb_permission)
     * name 字段没有唯一约束,因此返回列表
     *
     * @param name 角色名称
     * @return 角色列表(含权限)
     */
    List<RoleVO> selectRolesWithPermissionsByName(@Param("name") String name);

    /**
     * 根据多个角色名称批量查询角色详情并携带其所有权限(连表 tb_role + tb_role_permission + tb_permission)
     *
     * @param names 角色名称列表
     * @return 角色列表(含权限)
     */
    List<RoleVO> selectRolesWithPermissionsByNames(@Param("names") List<String> names);

}
