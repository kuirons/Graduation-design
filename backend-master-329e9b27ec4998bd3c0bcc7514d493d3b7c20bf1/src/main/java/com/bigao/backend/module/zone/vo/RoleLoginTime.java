package com.bigao.backend.module.zone.vo;

import java.sql.Timestamp;

/**
 * Created by wait on 2016/3/2.
 */
public class RoleLoginTime {
    private long roleId;
    private Timestamp time;
    private String state;

    public static RoleLoginTime valueOf(Timestamp timestamp, String state) {
        RoleLoginTime loginTime = new RoleLoginTime();
        loginTime.time = timestamp;
        loginTime.state = state;
        return loginTime;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "RoleLoginTime{" +
                "roleId=" + roleId +
                ", time=" + time +
                ", state='" + state + '\'' +
                '}';
    }
}
