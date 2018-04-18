package com.bigao.backend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.bigao.backend.module.sys.model.Role;
import com.bigao.backend.module.sys.model.RolePermission;
import com.bigao.backend.util.SqlUtil;

/**
 * Created by wait on 2015/11/27.
 */
@Component
public interface RolePermissionMapper {
    String TABLE_NAME = "gccp_role_permission";
    String ALL_FIELDS = "id,roleId,permissionId";
    String BRIEF_FIELDS = "roleId,permissionId";

    @Select(SqlUtil.SELECT_ALL + TABLE_NAME + " where roleId=#{0}")
    List<RolePermission> getRPByRoleId(int roleId);
    
    @Insert(SqlUtil.INSERT + TABLE_NAME + " (" + BRIEF_FIELDS + ") values(#{roleId},#{permissionId});")
    Integer addRolePermission(RolePermission RolePermission);
    
    @Delete(SqlUtil.DELETE + TABLE_NAME + " where roleId=#{id}")
    Integer deleteRolePermissionByRole(Role role);
    
    @Select(SqlUtil.SELECT_ALL+TABLE_NAME+" where roleId=#{id} "+" order by id asc ")
    List<RolePermission> getRolePermissionsByRole(Role role);
}
