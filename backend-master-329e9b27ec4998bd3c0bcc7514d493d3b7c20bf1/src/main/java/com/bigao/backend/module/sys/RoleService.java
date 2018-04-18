package com.bigao.backend.module.sys;

import com.bigao.backend.mapper.PermissionMapper;
import com.bigao.backend.mapper.RoleMapper;
import com.bigao.backend.mapper.RolePermissionMapper;
import com.bigao.backend.module.sys.model.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wait on 2015/11/30.
 */
@Service
public class RoleService {
    @Resource
    private RoleMapper roleDao;

    @Resource
    private RolePermissionMapper RPDao;

    @Resource
    private PermissionMapper permissionDao;

    public List<Role> getAllRole() throws Exception {
        return roleDao.getAllRole();
    }

    public PaginationResult<Role> getRoleList(PageInfo pageInfo) throws Exception {
        PaginationResult<Role> paginationResult = new PaginationResult<Role>(roleDao.getAllRoleCount(), roleDao.getAllRoleByPage(pageInfo));
        return paginationResult;
    }

    public void addRole(Role role, int roleId) throws Exception {
        role.setCreateTime(System.currentTimeMillis());
        roleDao.addRole(role);
        role = roleDao.getRoleByRoleName(role.getRoleName());
        List<RolePermission> rpList = RPDao.getRPByRoleId(roleId);
        for (RolePermission rolePermission : rpList) {
        	RPDao.addRolePermission(new RolePermission(role.getId(), rolePermission.getPermissionId()));
        }
    }

    public boolean roleNameIsExist(String roleName) throws Exception {
        boolean result = false;
        Role role = roleDao.getRoleByRoleName(roleName);
        if (role != null) {
            result = true;
        }
        return result;
    }


    public Integer deleteRole(int roleId) throws Exception {
        Role role = roleDao.getRoleById(roleId);
        return roleDao.deleteUser(role);
    }

    public List<Permission> getAllPermissionByRoleId(int roleId) throws Exception {
        List<Permission> permissionList = permissionDao.getAllPermission();
        List<RolePermission> rpList = RPDao.getRPByRoleId(roleId);
        Set<Integer> rolePermission = new HashSet<Integer>();
        for (RolePermission rp : rpList) {
            rolePermission.add(rp.getPermissionId());
        }
        for (Permission permission : permissionList) {
            if (rolePermission.contains(permission.getId())) {
                permission.setHasPermission(true);
            }
        }
        return permissionList;
    }

    public void updateRolePermission(int roleId, String permIds, String platformIds) throws Exception {
        String[] ids = permIds.split(",");
        List<RolePermission> rpList = new ArrayList<RolePermission>();
        if (permIds != null && permIds.length() != 0) {
            for (String id : ids) {
                RolePermission rp = new RolePermission(roleId, Integer.parseInt(id));
                rpList.add(rp);
            }
        }
        ids = platformIds.split(",");
        if (platformIds.length() != 0) {
            for (String id : ids) {
                if (!id.equals("0")) {
                    RolePlatform rp = new RolePlatform();
                    rp.setPlatformId(Integer.parseInt(id));
                    rp.setRoleId(roleId);
                }
            }
        }
    }
    
    public boolean usernameIsExist(String username) throws Exception {
        boolean result = false;
        Role role=roleDao.getRoleByRoleName(username);
        if (role != null) {
            result = true;
        }
        return result;
    }

	public Role getRoleById(int roleId) {
		return roleDao.getRoleById(roleId);
	}

	public Integer updateRole(Role model) {
		return roleDao.updateRole(model);
	}
}
