package com.bigao.backend.mapper;

import com.bigao.backend.module.sys.model.PageInfo;
import com.bigao.backend.module.sys.model.Permission;
import com.bigao.backend.util.SqlUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wait on 2015/11/27.
 */
@Component
public interface PermissionMapper {

    String TABLE_NAME = "gccp_permission";
    String ALL_FIELDS = "id,parentId,permissionName,permissionDesc,permissionURL,createTime";
    String BRIEF_FIELDS = "parentId,permissionName,permissionDesc,permissionURL,createTime";

    @Select(SqlUtil.SELECT_ALL + TABLE_NAME + SqlUtil.WHERE_ID)
    Permission getPermissionById(int id);

    @Select(SqlUtil.SELECT_ALL + TABLE_NAME + " where parentId <= 1 limit #{startRow},#{pageSize}")
    List<Permission> getAllParentPermissions(PageInfo pageInfo);

    @Select(SqlUtil.COUNT + TABLE_NAME + " where parentId <= 1")
    Integer getAllParentPermissinCount();

    @Select(SqlUtil.SELECT_ALL + TABLE_NAME + " order by id asc")
    List<Permission> getAllPermission();

    @Insert(SqlUtil.INSERT + TABLE_NAME + "(" + BRIEF_FIELDS + ") values(#{parentId},#{permissionName},#{permissionDesc},#{permissionURL},#{createTime})")
    Integer addPermission(Permission permission);


    @Select(SqlUtil.SELECT_ALL + TABLE_NAME + " where parentId=#{0} limit #{pageInfo.startRow},#{pageInfo.pageSize}")
    List<Permission> getChildPermissions(int parentId, @Param(value = "pageInfo") PageInfo pageInfo);

    @Select(SqlUtil.COUNT + TABLE_NAME + " where parentId=#{0}"+ " order by id asc")
    Integer getChildPermissionsCount(int parentId);
    
    @Select(SqlUtil.SELECT_ALL + TABLE_NAME + " where parentId=#{0}")
    List<Permission> getPermissionsByParentId(int parentId);

    @Update(SqlUtil.UPDATE + TABLE_NAME + " set parentId=#{parentId},permissionName=#{permissionName},permissionDesc=#{permissionDesc},permissionURL=#{permissionURL},createTime=#{createTime} " + SqlUtil.WHERE_ID)
    Integer updatePermission(Permission permission);
    
    @Select(SqlUtil.SELECT_ALL + TABLE_NAME + " where parentId <= 1" + " order by id asc" )
    List<Permission> getAllParentPermissionsss();
    
    @Delete(SqlUtil.DELETE+ TABLE_NAME+" where parentId=#{parentId} ")
    Integer deletePermissionByParentId(int parentId);
    
    @Delete(SqlUtil.DELETE + TABLE_NAME + " where id=#{id}")
    Integer deletePermission(Permission permission);
}

