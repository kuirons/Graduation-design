package com.bigao.backend.module.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bigao.backend.mapper.RolePermissionMapper;
import com.bigao.backend.module.sys.model.Role;
import com.bigao.backend.module.sys.model.RolePermission;

@Service
public class RolePermissionService {
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	public Integer deleteRolePermissionByRole(Role role) throws Exception {
		return rolePermissionMapper.deleteRolePermissionByRole(role);
    }
	
	public Integer saveRolePermission(RolePermission rolePermission)throws Exception{
		return rolePermissionMapper.addRolePermission(rolePermission);
	}
	
	public List<RolePermission> getRolePermissionsByRole(Role role)throws Exception{
		return rolePermissionMapper.getRolePermissionsByRole(role);
	}
}
