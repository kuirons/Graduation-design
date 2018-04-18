package com.bigao.backend.log;

import com.bigao.backend.common.anno.TableDesc;

import java.sql.Timestamp;

/**
 * @author wait
 * @date 2015年11月10日 下午5:10:33
 */
@TableDesc(value = "rolemaplog")
public class RoleMapLog {
    private int map;
    private int line;
    private String opt;
    private String action;
    private long serialNumber;
    private int platform;
    private int server;
    private long roleId;
    private Timestamp time;

    public int getMap() {
        return map;
    }

    public void setMap(int map) {
        this.map = map;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
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
        return "RoleMapLog{" +
                "map=" + map +
                ", line=" + line +
                ", opt='" + opt + '\'' +
                ", action='" + action + '\'' +
                ", serialNumber=" + serialNumber +
                ", platform=" + platform +
                ", server=" + server +
                ", roleId=" + roleId +
                ", time=" + time +
                '}';
    }
}
