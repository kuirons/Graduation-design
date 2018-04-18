package com.bigao.backend.module.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

/**
 * 权限管理 Created by wait on 2015/11/29.
 */
@Controller
@RequestMapping("permissionManage")
public class PermissionController {
	@Autowired
	private PermissionService permissionService;

	@RequestMapping(value = "permissionManage")
	public ModelAndView root() {
		return new ModelAndView("permission/permissionList");
	}

	@RequestMapping(value = "listPermission", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public ActionStringResult root(@RequestParam("echo") String echo, @RequestParam("pageSize") int pageSize,
			@RequestParam("startRow") int startRow) throws Exception {
		PageInfo pageInfo = PageInfo.valueOf(startRow, pageSize);
		PaginationResult<Permission> list = permissionService.getPermissionList(pageInfo);
		Map<String, Object> result = new HashMap<>();
		result.put("sEcho", echo);
		result.put("aaData", list.getResultList());
		result.put("iTotalRecords", list.getTotal());
		result.put("iTotalDisplayRecords", list.getTotal());
		return ActionStringResult.valueOf(JSON.toJSONString(result));
	}

	@RequestMapping(value = "childPermissionManage", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public ModelAndView childPermissionManage(@RequestParam("permissionId") int permissionId) throws Exception {
		ModelAndView view = new ModelAndView("permission/childPermissionList");
		view.addObject("permissionId", permissionId);
		return view;
	}

	@RequestMapping(value = "listChildPermission", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public ActionStringResult listChild(@RequestParam(value = "permissionId") int permissionId,
			@RequestParam(value = "echo") String echo, @RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "startRow") int startRow,HttpServletRequest request) throws Exception {
		PageInfo pageInfo = PageInfo.valueOf(startRow, pageSize);
		PaginationResult<Permission> list = permissionService.getChildPermissionList(permissionId, pageInfo);
		Map<String, Object> result = new HashMap<>();
		result.put("sEcho", echo);
		result.put("aaData", list.getResultList());
		result.put("iTotalRecords", list.getTotal());
		result.put("iTotalDisplayRecords", list.getTotal());
		return ActionStringResult.valueOf(JSON.toJSONString(result));
	}

	@RequestMapping(value = "addPermission")
	public ModelAndView addPermission() throws Exception {
		ModelAndView view = new ModelAndView("permission/addPermission");
		List<Permission> allPermissions=permissionService.getAllPermission();
		view.addObject("allPermissions", allPermissions);
		return view;
	}

	@RequestMapping(value = "addPermissionSubmit", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public ActionResultError listChildPermission(Permission permission) throws Exception {
		ActionResultError permResult=null;
		if(StringUtils.isBlank(permission.getPermissionName())){
			permResult=ActionResultError.err().addFiledError("permissionName", "权限名不能为空");
		}
		if(StringUtils.isBlank(permission.getPermissionDesc())){
			permResult=ActionResultError.err().addFiledError("permissionDesc", "权限描述不能为空");
		}
		if(permResult!=null){
			return permResult;
		}
		permission.setCreateTime(System.currentTimeMillis());
		Integer v=permissionService.addPermission(permission);
		if(v==null||v==0){
			return ActionResultError.err().addFiledError("permission.permissionName", "操作失败");
		}
		return ActionResultError.succ();
	}

	@RequestMapping(value = "editPermission", produces = { "application/json;charset=UTF-8" })
	public ModelAndView editPermission(@RequestParam(value = "permissionId") int permissionId) throws Exception {
		PageInfo pageInfo = new PageInfo(0, 999);
		PaginationResult<Permission> pageinaResult = permissionService.getPermissionList(pageInfo);
		List<Permission> permissionList = pageinaResult.getResultList();
		Permission permission = permissionService.getPermissionById(permissionId);
		ModelAndView view = new ModelAndView("permission/editPermission");
		view.addObject("permissionList", permissionList);
		view.addObject("parentId", permission.getParentId());
		view.addObject("permissionName", permission.getPermissionName());
		view.addObject("permissionDesc", permission.getPermissionDesc());
		view.addObject("permissionUrl", permission.getPermissionURL());
		return view;
	}

	@RequestMapping(value = "editPermissionSubmit", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public ActionResultError editPermissionSubmit(@RequestParam(value = "permission.parentId") int parentId,
			@RequestParam(value = "permission.permissionName") String permissionName,
			@RequestParam(value = "permission.permissionDesc") String permissionDesc,
			@RequestParam(value = "permission.permissionURL") String permissionURL) throws Exception {
		Permission permission = permissionService.getPermissionById(parentId);
		permission.setPermissionName(permissionName);
		permission.setPermissionDesc(permissionDesc);
		permission.setPermissionURL(permissionURL);

		Integer v = permissionService.updatePermission(permission);
		if (v == null || v == 0) {
			return ActionResultError.err().addFiledError("permission.permissionName", "修改失败");
		}
		return ActionResultError.succ();
	}

	@RequestMapping(value = "deletePermission", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public ActionResultError deletePermission(@RequestParam(value = "permissionId") int permissionId) throws Exception {
		Permission permission=permissionService.getPermissionById(permissionId);
		if(permission==null){
			return ActionResultError.err().addFiledError("permissionName", "删除失败,没有这个用户");
		}
		Integer v = permissionService.deletePermission(permission);
		if (v == null || v == 0) {
			return ActionResultError.err().addFiledError("permissionName", "删除失败");
		}else{
			deletePermissionByParentId(permission.getId());
		}
		return ActionResultError.succ();
	}
	
	private void deletePermissionByParentId(int parentId) throws Exception{
		List<Permission> permissions=permissionService.getPermissionByParentId(parentId);
		if(permissions!=null&&permissions.size()!=0){
			for (Permission permission : permissions) {
				permissionService.deletePermission(permission);
				deletePermissionByParentId(permission.getId());
			}
		}
	}
	
}
