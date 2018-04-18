package com.bigao.backend.log;

import com.bigao.backend.common.anno.TableDesc;

import java.sql.Timestamp;

/**
 * Created by wait on 2016/1/10.
 */
@TableDesc("olduserlog")
public class OldUserLog {
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
}
