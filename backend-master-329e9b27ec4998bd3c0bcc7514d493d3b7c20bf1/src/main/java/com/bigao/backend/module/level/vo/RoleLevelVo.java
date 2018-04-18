package com.bigao.backend.module.level.vo;

/**
 * Created by wait on 2015/12/28.
 */
public class RoleLevelVo {
    private long roleId;
    private int level;

    public long getRoleId() {
        return roleId;
    }

    public RoleLevelVo setRoleId(long roleId) {
        this.roleId = roleId;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public RoleLevelVo setLevel(int level) {
        this.level = level;
        return this;
    }

    @Override
    public String toString() {
        return "RoleLevelVo{" +
                "roleId=" + roleId +
                ", level=" + level +
                '}';
    }
}
