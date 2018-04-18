package com.bigao.backend.module.sys.model;

public class Role {

    private int id;
    private String roleName;
    private String description;
    private long createTime;

    public Role() {
    }

    public static Role valueOf(String roleName, String description, long createTime) {
        Role role = new Role();
        role.roleName = roleName;
        role.description = description;
        role.createTime = createTime;
        return role;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }


}
