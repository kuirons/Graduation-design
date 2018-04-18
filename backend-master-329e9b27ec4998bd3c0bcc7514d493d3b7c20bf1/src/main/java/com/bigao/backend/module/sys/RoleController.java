package com.bigao.backend.module.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.bigao.backend.common.ActionResultError;
import com.bigao.backend.common.ActionStringResult;
import com.bigao.backend.module.sys.model.PageInfo;
import com.bigao.backend.module.sys.model.PaginationResult;
import com.bigao.backend.module.sys.model.Permission;
import com.bigao.backend.module.sys.model.Role;
import com.bigao.backend.module.sys.model.RolePermission;

/**
 * 角色管理
 * Created by wait on 2015/11/30.
 */
@Controller
@RequestMapping(value = "roleManage")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private PermissionService permissionService;
    
    @RequestMapping(value = "roleManage")
    public ModelAndView root() {
        return new ModelAndView("role/roleList");
    }

    @RequestMapping(value = "listRole", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ActionStringResult listRole(@RequestParam(value = "pageSize") int pageSize,
                                       @RequestParam(value = "startRow") int startRow) throws Exception {
        PageInfo pageInfo = new PageInfo(startRow, pageSize);
        PaginationResult<Role> result = roleService.getRoleList(pageInfo);
        Map<String, Object> data = new HashMap<>();
        data.put("aaData", result.getResultList());
        data.put("iTotalRecords", result.getTotal());
        data.put("iTotalDisplayRecords", result.getTotal());
        return ActionStringResult.valueOf(JSON.toJSONString(data));
    }

    @RequestMapping(value = "addRole", produces = {"application/json;charset=UTF-8"})
    public ModelAndView addRole(Model model) throws Exception {
        ModelAndView view = new ModelAndView("role/addRole");
        view.addObject("roleList", roleService.getAllRole());
        model.addAttribute("role", new Role());
        return view;
    }

    @RequestMapping(value = "addRoleSubmit", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ActionResultError addRoleSubmit(@RequestParam(value = "role.roleName") String roleName,
                                           @RequestParam(value = "role.description") String description,
                                           @RequestParam(value = "id") int roleId) throws Exception {
    	Role role=Role.valueOf(roleName, description, System.currentTimeMillis());
        ActionResultError userResult = null;
        if(role.getRoleName()==null||role.getRoleName().equals("")){
        	userResult = ActionResultError.err();
        	userResult.getFieldErrors().put("username", "用户名不能为空");
        }else if (roleService.usernameIsExist(role.getRoleName())) {
            userResult = ActionResultError.err();
            userResult.getFieldErrors().put("role.roleName", "该用户名已存在");
        }
        if (role.getDescription() == null || role.getDescription().equals("")) {
            userResult = ActionResultError.err();
            userResult.getFieldErrors().put("role.description", "角色描述不能为空");
        } 
        if (userResult != null) {
            return userResult;
        }
        roleService.addRole(role, roleId);
        userResult = ActionResultError.succ();
        return userResult;
    }

    @RequestMapping(value = "deleteRole", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ActionResultError deleteRole(@RequestParam(value = "roleId") int roleId) throws Exception {
    	Role role=roleService.getRoleById(roleId);
    	if(role==null){
    		return ActionResultError.err();
    	}
        Integer v = roleService.deleteRole(roleId);
        if (v == null || v == 0) {
            return ActionResultError.err().addFiledError("roleId", "操作失败");
        }else{
        	rolePermissionService.deleteRolePermissionByRole(role);
        }
        return ActionResultError.succ();
    }
    
    @RequestMapping(value="editRole",method=RequestMethod.GET)
    public String edit(@RequestParam(value="roleId") int roleId,Model model)throws Exception{
    	Role role=roleService.getRoleById(roleId);
    	if(role==null){
    		model.addAttribute("usernameError", "用户名不存在");
    	}else{
    		model.addAttribute("role", role);
    	}
    	return "role/editRole";
    }
    
    @RequestMapping(value = "editRoleSubmit", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ActionResultError editUser(Role role) throws Exception {
        ActionResultError roleResult=null;
        if(role.getDescription()==null||role.getDescription().trim().equals("")){
        	roleResult=ActionResultError.err().addFiledError("role.description", "角色描述不能为空");
        }
        if(roleResult!=null){
        	return roleResult;
        }
        Role model=roleService.getRoleById(role.getId());
        model.setRoleName(role.getRoleName());
        model.setDescription(role.getDescription());
        Integer v =roleService.updateRole(model);
        if (v == null || v == 0) {
            return ActionResultError.err().addFiledError("user.username", "更新失败");
        }
        return ActionResultError.succ();
    }
    
    @RequestMapping(value = "assignPermission")
    public String assignPermission(@RequestParam(value="roleId") int roleId,Model model) throws Exception{
    	Role role=roleService.getRoleById(roleId);
        List<Permission> allPermissions=permissionService.getAllPermission();
        List<RolePermission> rolePermissions=rolePermissionService.getRolePermissionsByRole(role);
        for (int i = 0; i < allPermissions.size(); i++) {
        	for (RolePermission rolePermission : rolePermissions) {
        		if(allPermissions.get(i).getId()==rolePermission.getPermissionId()){
        			allPermissions.get(i).setHasPermission(true);
        		}
    		}
		}
    	model.addAttribute("allPermissions", allPermissions);
    	model.addAttribute("role", role);
    	return "role/assignPermission";
    }
    
    @RequestMapping(value="assignPermissionSubmit",method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ActionResultError savePermission(@RequestParam(value="roleId") int roleId,
    										@RequestParam(value="permIds") String permId)throws Exception{
    	Role role=roleService.getRoleById(roleId);
    	if(role==null){
    		return ActionResultError.err();
    	}
    	if(permId==null){
    		return ActionResultError.err();
    	}
    	if(permId.trim().equals("")){
    		return ActionResultError.succ();
    	}
    	rolePermissionService.deleteRolePermissionByRole(role);
    	String[] permIds=permId.split(",");
    	for (String psId : permIds) {
			rolePermissionService.saveRolePermission(new RolePermission(role.getId(),Integer.valueOf(psId)));
		}
    	
    	return ActionResultError.succ();
    }
}
