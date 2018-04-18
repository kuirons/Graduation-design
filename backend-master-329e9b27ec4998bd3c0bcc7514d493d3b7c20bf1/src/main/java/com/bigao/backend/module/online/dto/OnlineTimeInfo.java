package com.bigao.backend.module.online.dto;

/**
 * Created by wait on 2015/12/1.
 */
public class OnlineTimeInfo {
    private transient long roleId;
    /** 账号 */
    private String account;
    /** 角色名称 */
    private String roleName;
    /** 等级 */
    private int level;
    /** 开启计算时间 */
    private String startTime;
    /** 在线时长[秒] */
    private int onlineTime;

    public long getRoleId() {
        return roleId;
    }

    public OnlineTimeInfo setRoleId(long roleId) {
        this.roleId = roleId;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public OnlineTimeInfo setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getRoleName() {
        return roleName;
    }

    public OnlineTimeInfo setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public OnlineTimeInfo setLevel(int level) {
        this.level = level;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public OnlineTimeInfo setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public int getOnlineTime() {
        return onlineTime;
    }

    public OnlineTimeInfo setOnlineTime(int onlineTime) {
        this.onlineTime = onlineTime;
        return this;
    }

    @Override
    public String toString() {
        return "OnlineTimeInfo{" +
                "account='" + account + '\'' +
                ", roleName='" + roleName + '\'' +
                ", level=" + level +
                ", startTime='" + startTime + '\'' +
                ", onlineTime=" + onlineTime +
                '}';
    }
}
