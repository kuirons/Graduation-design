package com.bigao.backend.log;

import com.bigao.backend.common.anno.TableDesc;

@TableDesc(value = "roleclientnodelog")
public class RoleClientNodeLog {
    private long serialNumber;
    private int platform;
    private int server;
    private long roleId;
    private int node; // 节点id

    public RoleClientNodeLog() {

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

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }
}
