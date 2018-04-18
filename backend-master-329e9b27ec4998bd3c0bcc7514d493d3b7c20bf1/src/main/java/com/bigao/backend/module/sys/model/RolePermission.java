package com.bigao.backend.module.sys.model;

public class RolePermission {

    private int id;
    private int roleId;
    private int permissionId;

    public RolePermission() {
    }

    public RolePermission(int roleId, int permissionId) {
        super();
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

}
