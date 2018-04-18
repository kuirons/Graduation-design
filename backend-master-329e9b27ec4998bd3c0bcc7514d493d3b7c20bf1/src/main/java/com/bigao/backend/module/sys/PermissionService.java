package com.bigao.backend.module.sys;

import com.bigao.backend.mapper.PermissionMapper;
import com.bigao.backend.module.sys.model.PageInfo;
import com.bigao.backend.module.sys.model.PaginationResult;
import com.bigao.backend.module.sys.model.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by wait on 2015/11/26.
 */
@Service
public class PermissionService {

    @Resource
    private PermissionMapper permDao;


    public PaginationResult<Permission> getPermissionList(PageInfo pageInfo)
            throws Exception {
        return new PaginationResult<>(permDao.getAllParentPermissinCount(), permDao.getAllParentPermissions(pageInfo));
    }

    public Integer addPermission(Permission permission) throws Exception {
        permission.setCreateTime(System.currentTimeMillis());
        return permDao.addPermission(permission);
    }

    public PaginationResult<Permission> getChildPermissionList(int parentId, PageInfo pageInfo) throws Exception {
        return new PaginationResult<>(permDao.getChildPermissionsCount(parentId), permDao.getChildPermissions(parentId, pageInfo));
    }

    public Permission getPermissionById(int id) throws Exception {
        return permDao.getPermissionById(id);
    }

    public Integer updatePermission(Permission permission) throws Exception {
        Permission oldPermission = permDao.getPermissionById(permission.getId());
        permission.setCreateTime(oldPermission.getCreateTime());
        return permDao.updatePermission(permission);
    }

    public Integer deletePermission(Permission permission) throws Exception {
        return permDao.deletePermission(permission);
    }

    public Map<String, List<String>> getPermissionUrlMap() throws Exception {
        List<Permission> perList = permDao.getAllPermission();
        Map<String, List<String>> resultMap = new HashMap<String, List<String>>();
        for (Permission permission : perList) {
            List<String> permissionList = resultMap.get(permission.getPermissionURL());
            if (permissionList == null) {
                permissionList = new ArrayList<String>();
                permissionList.add(permission.getPermissionName());
                resultMap.put(permission.getPermissionURL(), permissionList);
            } else {
                permissionList.add(permission.getPermissionName());
            }
        }
        return resultMap;
    }
    
    public List<Permission> getPermissionByParentId(int parentId) throws Exception{
    	return permDao.getPermissionsByParentId(parentId);
    }
    
    public List<Permission> getAllPermission() throws Exception{
    	return permDao.getAllPermission();
    }
    
    public List<Permission> getAllParentPermission() {
		return permDao.getAllParentPermissionsss();
	}

	public Integer deletePermissionByParentId(int parentId) {
		return permDao.deletePermissionByParentId(parentId);
	}
}
