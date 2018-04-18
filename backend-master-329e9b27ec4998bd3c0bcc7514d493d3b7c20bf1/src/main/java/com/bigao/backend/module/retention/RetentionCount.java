package com.bigao.backend.module.retention;

import java.sql.Timestamp;

/**
 * 用来作为留存统计的数量
 * Created by wait on 2016/1/20.
 */
public class RetentionCount {
    private long roleId;
    private Timestamp time;

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

    @Override
    public String toString() {
        return "RetentionCount{" +
                "roleId=" + roleId +
                ", time=" + time +
                '}';
    }
}
