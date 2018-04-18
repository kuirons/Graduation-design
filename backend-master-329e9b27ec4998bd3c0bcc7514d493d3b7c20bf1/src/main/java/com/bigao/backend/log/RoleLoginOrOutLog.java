package com.bigao.backend.log;

import com.bigao.backend.common.anno.TableDesc;

import java.sql.Timestamp;

/**
 * Created by wait on 2015/12/28.
 */
@TableDesc(value = "roleloginoroutlog")
public class RoleLoginOrOutLog {

    public static final String LOGIN = "LOGIN"; // 登录
    public static final String LOGOUT = "LOGOUT"; // 登出

    private String state;
    private long serialNumber;
    private int platform;
    private int server;
    private long roleId;
    private Timestamp time;
    private String ip;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "RoleLoginOrOutLog{" +
                "state='" + state + '\'' +
                ", serialNumber=" + serialNumber +
                ", platform=" + platform +
                ", server=" + server +
                ", roleId=" + roleId +
                ", time=" + time +
                ", ip='" + ip + '\'' +
                '}';
    }
}
