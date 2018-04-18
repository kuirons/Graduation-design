package com.bigao.backend.module.sys;

import com.bigao.backend.mapper.PermissionMapper;
import com.bigao.backend.mapper.RoleMapper;
import com.bigao.backend.mapper.RolePermissionMapper;
import com.bigao.backend.mapper.UserMapper;
import com.bigao.backend.module.sys.model.*;
import com.bigao.backend.util.MD5Util;
import com.bigao.backend.util.SystemConfig;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wait on 2015/11/29.
 */
@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    /** 缓存300个用户 */
    private Cache<String, User> userCache = CacheBuilder.newBuilder().maximumSize(30).build();
    private Cache<Integer, List<RolePermission>> rpCache = CacheBuilder.newBuilder().maximumSize(30).build();
    private List<Permission> allPermission;

    public void clearCache() {
        userCache.cleanUp();
        rpCache.cleanUp();
        allPermission = null;

        logger.info("user cache clear...");
    }

    public User getUserByName(String userName) {
        User user = userCache.getIfPresent(userName);
        if (user == null) {
            user = userMapper.getUserByName(userName);
            if (user != null) {
                userCache.put(userName, user);
            }
        }
        return user;
    }

    public List<Permission> getAllPermission() {
        if (allPermission == null || allPermission.isEmpty()) {
            allPermission = permissionMapper.getAllPermission();
        }
        return allPermission;
    }

    public Permission getPermissionById(int permissionId) {
        List<Permission> allPermission = getAllPermission();
        for (Permission p : allPermission) {
            if (p.getId() == permissionId) {
                return p;
            }
        }
        return null;
    }

    public List<RolePermission> getRPByRoleId(int roleId) {
        List<RolePermission> rp = rpCache.getIfPresent(roleId);
        if (rp == null) {
            List<RolePermission> rolePermissions = rolePermissionMapper.getRPByRoleId(roleId);
            if (rolePermissions != null && !rolePermissions.isEmpty()) {
                rpCache.put(roleId, rolePermissions);
            }
            return rolePermissions;
        }
        return rp;
    }

    public PaginationResult<User> getUserList(PageInfo pageInfo) throws Exception {
        List<User> userList = userMapper.getAllUsers(pageInfo);
        for (User user : userList) {
            user.setRoleName(roleMapper.getRoleById(user.getRoleId()).getDescription());
        }
        return new PaginationResult<>(userMapper.getAllUsersCount(), userList);
    }

    public boolean usernameIsExist(String username) throws Exception {
        boolean result = false;
        User user = userMapper.getUserByName(username);
        if (user != null) {
            result = true;
        }
        return result;
    }

    public void addUser(User user) throws Exception {
        String password = MD5Util.getMD5String(user.getPassword() + SystemConfig.getProperty("system.security"));
        user.setPassword(password);
        user.setCreateTime(System.currentTimeMillis());
        user.setDeleted(false);
        userMapper.addUser(user);

    }

    public Integer deleteUser(int userId) throws Exception {
        User user = userMapper.getUserById(userId);
        user.setDeleted(true);
        return userMapper.deleteUser(user);
    }

    public User getUserById(int userId) throws Exception {
        return userMapper.getUserById(userId);

    }

    public Integer updateUser(User user) throws Exception {
        return userMapper.updateUser(user);
    }
}
