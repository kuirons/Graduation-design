package com.bigao.backend.module.login;

import com.bigao.backend.module.sys.UserService;
import com.bigao.backend.module.sys.auth.AuthenticationResult;
import com.bigao.backend.module.sys.model.Permission;
import com.bigao.backend.module.sys.model.RolePermission;
import com.bigao.backend.module.sys.model.User;
import com.bigao.backend.util.MD5Util;
import com.bigao.backend.util.SystemConfig;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wait on 2015/11/26.
 */
@Service
public class LoginService {

    @Autowired
    private UserService userService;


    public boolean checkLogin(String userName, String password) throws Exception {
        boolean result = false;

        User user = userService.getUserByName(userName);
        if (user != null) {
            if (MD5Util.getMD5String(password + SystemConfig.getProperty("system.security")).equals(user.getPassword())) {
                result = true;
            }
        }
        return result;
    }

    public Map<String, List<String>> getUrlPermissionMap() throws Exception {
        Map<String, List<String>> urlPermissionMap = new HashMap<String, List<String>>();
        //取得权限表
        List<Permission> permissionList = userService.getAllPermission();
        for (Permission permission : permissionList) {
            List<String> perList = new ArrayList<String>();
            perList.add(permission.getPermissionName());
            urlPermissionMap.put(permission.getPermissionURL(), perList);
        }

        return urlPermissionMap;
    }

    public List<String> getRolePermission(String userName) throws Exception {
        User user = userService.getUserByName(userName);
        List<RolePermission> rolePermissions = userService.getRPByRoleId(user.getRoleId());
        List<String> p = Lists.newArrayList();
        for (RolePermission rolePermission : rolePermissions) {
            Permission permission = userService.getPermissionById(rolePermission.getPermissionId());
            if (permission != null && !StringUtils.isBlank(permission.getPermissionName())) {
                p.add(permission.getPermissionName());
            }
        }
        return p;
    }

    public AuthenticationResult authenticate(String userName, String password) throws Exception {
        AuthenticationResult result = new AuthenticationResult();
        if (checkLogin(userName, password)) {
            result.setCode(1);

            result.setPermissionList(getRolePermission(userName));
            result.setUrlPermissionMap(getUrlPermissionMap());
        } else {
            result.setCode(-1);
        }
        return result;
    }
}
