package com.bigao.backend.log;

import com.bigao.backend.common.anno.TableDesc;

import java.sql.Timestamp;

/**
 * 玩家升级日志
 * Created by wait on 2015/12/13.
 */
@TableDesc(value = "roleleveluplog")
public class RoleLevelUpLog {
    private int oldLevel;
    private long oldExp;
    private int newLevel;
    private long newExp;
    private String action;
    private long serialNumber;
    private int platform;
    private int server;
    private long roleId;
    private Timestamp time;


    public long getNewExp() {
        return newExp;
    }

    public void setNewExp(long newExp) {
        this.newExp = newExp;
    }

    public int getOldLevel() {
        return oldLevel;
    }

    public void setOldLevel(int oldLevel) {
        this.oldLevel = oldLevel;
    }

    public long getOldExp() {
        return oldExp;
    }

    public void setOldExp(long oldExp) {
        this.oldExp = oldExp;
    }

    public int getNewLevel() {
        return newLevel;
    }

    public void setNewLevel(int newLevel) {
        this.newLevel = newLevel;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(long serialNumber) {
        this.serialNumber = serialNumber;
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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "RoleLevelUpLog{" +
                "oldLevel=" + oldLevel +
                ", oldExp=" + oldExp +
                ", newLevel=" + newLevel +
                ", newExp=" + newExp +
                ", action='" + action + '\'' +
                ", serialNumber=" + serialNumber +
                ", platform=" + platform +
                ", server=" + server +
                ", roleId=" + roleId +
                ", time=" + time +
                '}';
    }
}
