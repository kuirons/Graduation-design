package com.bigao.backend.log;

import com.bigao.backend.common.anno.LogType;
import com.bigao.backend.common.anno.TableDesc;

/**
 * Created by wait on 2015/11/30.
 */
@TableDesc(value = "rolecreatelog", logType = LogType.YEAR)
public class RoleCreateLog {

    private String roleName;
    private String race;
    private int platform;
    private int server;
    private long roleId;
    private long createTime;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public int getServer() {
        return server;
    }

    public void setServer(int server) {
        this.server = server;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "RoleCreateLog{" +
                "roleName='" + roleName + '\'' +
                ", race='" + race + '\'' +
                ", platform=" + platform +
                ", server=" + server +
                ", roleId=" + roleId +
                ", createTime=" + createTime +
                '}';
    }
}
