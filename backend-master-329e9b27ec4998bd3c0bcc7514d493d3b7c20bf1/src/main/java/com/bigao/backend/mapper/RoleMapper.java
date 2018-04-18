package com.bigao.backend.mapper;

import com.bigao.backend.module.sys.model.PageInfo;
import com.bigao.backend.module.sys.model.Role;
import com.bigao.backend.module.sys.model.User;
import com.bigao.backend.util.SqlUtil;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wait on 2015/11/29.
 */
@Component
public interface RoleMapper {
    String TABLE_NAME = "gccp_role";
    String ALL_FIELDS = "id,roleName,description,createTime";

    @Select(SqlUtil.SELECT_ALL + TABLE_NAME + " where id=#{0}")
    Role getRoleById(int id);

    @Select(SqlUtil.SELECT_ALL + TABLE_NAME)
    List<Role> getAllRole();

    @Select(SqlUtil.SELECT_ALL + TABLE_NAME + " limit #{startRow},#{pageSize}")
    List<Role> getAllRoleByPage(PageInfo pageInfo);


    @Select("select count(*) from " + TABLE_NAME)
    Integer getAllRoleCount();

    @Insert(SqlUtil.INSERT + TABLE_NAME + " (" + ALL_FIELDS + ") values(#{id},#{roleName},#{description},#{createTime});")
    Integer addRole(Role role);

    @Select(SqlUtil.SELECT_ALL + TABLE_NAME + " where roleName=#{0}")
    Role getRoleByRoleName(String roleName);

    @Delete(SqlUtil.DELETE + TABLE_NAME + " where id=#{id}")
    Integer deleteUser(Role role);
    
    @Update(SqlUtil.UPDATE + TABLE_NAME + " set roleName=#{roleName},description=#{description},createTime=#{createTime} where id=#{id}")
    Integer updateRole(Role role);
}
