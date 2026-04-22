package com.object.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.object.usercenter.model.po.RolePermissionPO;
import com.object.usercenter.model.vo.RolePermissionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermissionPO> {

    /**
     * 查询所有角色-权限的对照明细(连表 tb_role_permission + tb_role + tb_permission)
     *
     * @return 角色权限对照明细列表
     */
    List<RolePermissionVO> selectAllRolePermissionDetails();

    /**
     * 根据角色id查询其所有角色-权限对照明细(连表 tb_role_permission + tb_role + tb_permission)
     *
     * @param roleId 角色id
     * @return 角色权限对照明细列表
     */
    List<RolePermissionVO> selectRolePermissionDetailsByRoleId(@Param("roleId") String roleId);

    /**
     * 根据权限id反查哪些角色拥有该权限(连表 tb_role_permission + tb_role + tb_permission)
     *
     * @param permissionId 权限id
     * @return 角色权限对照明细列表
     */
    List<RolePermissionVO> selectRolePermissionDetailsByPermissionId(@Param("permissionId") String permissionId);

}
