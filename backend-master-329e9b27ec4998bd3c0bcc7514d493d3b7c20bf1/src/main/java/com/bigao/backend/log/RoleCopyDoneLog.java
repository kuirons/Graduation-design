package com.bigao.backend.log;

import com.bigao.backend.common.anno.TableDesc;

import java.sql.Timestamp;

/**
 * Created by wait on 2015/12/28.
 */
@TableDesc(value = "rolecopydonelog")
public class RoleCopyDoneLog {
    private String copyType;
    private int copyModel;
    private String extra;
    private long serialNumber;
    private int platform;
    private int server;
    private long roleId;
    private Timestamp time;

    public String getCopyType() {
        return copyType;
    }

    public void setCopyType(String copyType) {
        this.copyType = copyType;
    }

    public int getCopyModel() {
        return copyModel;
    }

    public void setCopyModel(int copyModel) {
        this.copyModel = copyModel;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
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
}
