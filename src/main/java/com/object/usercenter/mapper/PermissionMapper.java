package com.object.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.object.usercenter.model.po.PermissionPO;
import com.object.usercenter.model.vo.PermissionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<PermissionPO> {

    /**
     * 根据角色id查询该角色拥有的所有权限(连表 tb_role_permission + tb_permission)
     *
     * @param roleId 角色id
     * @return 权限列表
     */
    List<PermissionVO> selectPermissionsByRoleId(@Param("roleId") String roleId);

    /**
     * 根据角色key查询该角色拥有的所有权限(连表 tb_role + tb_role_permission + tb_permission)
     *
     * @param roleKey 角色key
     * @return 权限列表
     */
    List<PermissionVO> selectPermissionsByRoleKey(@Param("roleKey") String roleKey);

    /**
     * 根据多个角色key批量查询对应的所有权限(去重)
     *
     * @param roleKeys 角色key列表
     * @return 权限列表
     */
    List<PermissionVO> selectPermissionsByRoleKeys(@Param("roleKeys") List<String> roleKeys);

}
