package com.bigao.backend.module.sys.model;

public class Permission {

    private int id;
    private int parentId;
    private String permissionName;
    private String permissionDesc;
    private String permissionURL;
    private long createTime;
    private boolean hasPermission;

    public static Permission valueOf(int parentId, String permissionName, String permissionDesc, String permissionURL,
                                     long createTime) {
        Permission p = new Permission();
        p.parentId = parentId;
        p.permissionName = permissionName;
        p.permissionDesc = permissionDesc;
        p.permissionURL = permissionURL;
        p.createTime = createTime;
        return p;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionDesc() {
        return permissionDesc;
    }

    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }

    public String getPermissionURL() {
        return permissionURL;
    }

    public void setPermissionURL(String permissionURL) {
        this.permissionURL = permissionURL;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(boolean hasPermission) {
        this.hasPermission = hasPermission;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
