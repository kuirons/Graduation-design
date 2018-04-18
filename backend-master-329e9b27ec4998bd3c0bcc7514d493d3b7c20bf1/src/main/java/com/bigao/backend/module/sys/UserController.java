package com.bigao.backend.module.sys;

import com.alibaba.fastjson.JSON;
import com.bigao.backend.common.ActionResultError;
import com.bigao.backend.common.ActionStringResult;
import com.bigao.backend.module.sys.model.PageInfo;
import com.bigao.backend.module.sys.model.PaginationResult;
import com.bigao.backend.module.sys.model.User;
import com.bigao.backend.util.MD5Util;
import com.bigao.backend.util.SystemConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统用户管理
 * Created by wait on 2015/11/29.
 */
@Controller
@RequestMapping(value = "userManage")
public class UserController {

    @Autowired
    private UserService userService;
   
    @Autowired
    private RoleService roleService; 

    @RequestMapping(value = "userManage")
    public ModelAndView root() {
        return new ModelAndView("user/userList");
    }

    @RequestMapping(value = "listUser", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ActionStringResult listUser(@RequestParam(value = "pageSize") int pageSize,
                                       @RequestParam(value = "startRow") int startRow) throws Exception {
        PageInfo pageInfo = PageInfo.valueOf(startRow, pageSize);
        PaginationResult<User> paginationResult = userService.getUserList(pageInfo);
        Map<String, Object> result = new HashMap<>();
        result.put("aaData", paginationResult.getResultList());
        result.put("iTotalRecords", paginationResult.getTotal());
        result.put("iTotalDisplayRecords", paginationResult.getTotal());
        return ActionStringResult.valueOf(JSON.toJSONString(result));
    }

    @RequestMapping(value = "addUser", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public String addUser(Model model) throws Exception {
    	model.addAttribute("roleList", roleService.getAllRole());
    	model.addAttribute("user", new User());
        return "user/addUser";
    }

    @RequestMapping(value = "addUserSubmit", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ActionResultError addUserSubmit(@RequestParam(value = "user.username") String username,
                                           @RequestParam(value = "user.password") String password,
                                           @RequestParam(value = "confirmPassword") String confirmPassword,
                                           @RequestParam(value = "user.roleId") int roleId) throws Exception {
        User user = new User(username, password, roleId, false, System.currentTimeMillis());
        ActionResultError userResult = null;
        if (user.getUsername() == null || user.getUsername().equals("")) {
            userResult = ActionResultError.err();
            userResult.getFieldErrors().put("username", "用户名不能为空");
        } else if (userService.usernameIsExist(user.getUsername())) {
            userResult = ActionResultError.err();
            userResult.getFieldErrors().put("user.username", "该用户名已存在");
        }
        if (user.getPassword() == null || user.getPassword().equals("")) {
            userResult = ActionResultError.err();
            userResult.getFieldErrors().put("user.password", "密码不能为空");
        } else if (user.getPassword().length() < 6) {
            userResult = ActionResultError.err();
            userResult.getFieldErrors().put("user.password", "密码不能低于6位");
        }
        if (confirmPassword == null || confirmPassword.equals("")) {
            userResult = ActionResultError.err();
            userResult.getFieldErrors().put("confirmPassword", "请确认密码");
        } else if (!confirmPassword.equals(user.getPassword())) {
            userResult = ActionResultError.err();
            userResult.getFieldErrors().put("confirmPassword", "两次输入密码不一致");
        }
        if (user.getRoleId() == -1) {
            userResult = ActionResultError.err();
            userResult.getFieldErrors().put("user.roleId", "请选择角色");
        }

        if (userResult != null) {
            return userResult;
        }

        userService.addUser(user);
        userResult = ActionResultError.succ();
        return userResult;
    }

    @RequestMapping(value = "deleteUser", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ActionStringResult delete(@RequestParam(value = "userId") int userId) throws Exception {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ActionStringResult.err();
        }
        Integer v = userService.deleteUser(userId);
        if (v == null || v == 0) {
            return ActionStringResult.err();
        }
        return ActionStringResult.succ();
    }

    @RequestMapping(value = "editUser", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam(value = "userId") int userId) throws Exception {
        User user = userService.getUserById(userId);
        ModelAndView view = new ModelAndView("/user/editUser");
        view.addObject("userId", userId);
        if (user == null) {
            view.addObject("usernameError", "用户不存在");
        } else {
            view.addObject("userName", user.getUsername());
            view.addObject("roleId", user.getRoleId());
        }

        return view;
    }

    @RequestMapping(value = "editUserSubmit", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ActionResultError editUser(@RequestParam(value = "user.id") int userId,
                                      @RequestParam(value = "user.username") String username,
                                      @RequestParam(value = "user.password") String password,
                                      @RequestParam(value = "confirmPassword") String confirmPassword,
                                      @RequestParam(value = "user.roleId") int roleId) throws Exception {
    	
    	ActionResultError userResult = null;
        if (password == null || password.equals("")) {
        	userResult=ActionResultError.err();
            userResult.addFiledError("user.password", "密码不能为空");
        } else if (password.length() < 6) {
        	userResult=ActionResultError.err();
            userResult.addFiledError("user.password", "密码不能低于6位");
        }

        if (confirmPassword == null || confirmPassword.equals("")) {
        	userResult=ActionResultError.err();
            userResult.addFiledError("confirmPassword", "确认密码不能为空");
        }
        if (!confirmPassword.equals(password)) {
        	userResult=ActionResultError.err();
            userResult.addFiledError("confirmPassword", "两次输入密码不一致");
        }
        if (roleId == -1) {
        	userResult=ActionResultError.err();
            userResult.addFiledError("user.roleId", "请选择角色");
        }
        
        if (userResult != null) {
            return userResult;
        }
        User user=userService.getUserById(userId);
        user.setPassword(MD5Util.getMD5String(password + SystemConfig.getProperty("system.security")));
        user.setRoleId(roleId);
        Integer v = userService.updateUser(user);
        if (v == null || v == 0) {
            return ActionResultError.err().addFiledError("user.username", "更新失败");
        }
        return ActionResultError.succ();
    }
}

