package com.bigao.backend.log;

import com.bigao.backend.common.anno.TableDesc;

import java.sql.Timestamp;

/**
 * 充值日志
 * Created by wait on 2016/1/2.
 */
@TableDesc("roleexchangelog")
public class RoleExchangeLog {
    private String order;
    private int exchangeType;
    private int rmb;
    private int gold;
    private long serialNumber;
    private int platform;
    private int server;
    private long roleId;
    private Timestamp time;
    /** 用于联表查询时的设置的字段 */
    private int sumRmb;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(int exchangeType) {
        this.exchangeType = exchangeType;
    }

    public int getRmb() {
        return rmb;
    }

    public void setRmb(int rmb) {
        this.rmb = rmb;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
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

    public int getSumRmb() {
        return sumRmb;
    }

    public void setSumRmb(int sumRmb) {
        this.sumRmb = sumRmb;
    }
}
