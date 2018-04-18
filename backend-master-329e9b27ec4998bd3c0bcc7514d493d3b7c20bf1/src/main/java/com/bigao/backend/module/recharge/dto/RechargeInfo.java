package com.bigao.backend.module.recharge.dto;

import com.bigao.backend.module.common.PlatformServerInfo;

/**
 * Created by wait on 2015/12/13.
 */
public class RechargeInfo extends PlatformServerInfo {
    /** 名字 */
    private String firstColumnName;
    /** 对应区服的开服日期 */
    private String openDate;
    /** 对应区服的开服天数 */
    private int openDays;
    /** 对应区服的选择时间内的充值RMB总数 */
    private int rmb;
    /** 对应区服从开服到当前的总充值RMB */
    private int sumRmb;
    /** 对应区服的选择时间内的充值人数 */
    private int rechargeNum;
    /** 对应区服的选择时间内的充值人数在注册人数中的占比 */
    private String rechargeRate;
    /** 对应区服的选择时间内有重复付费的用户需记录，每个用户只记录一次。 */
    private int repeatRechargeNum;
    /** 对应区服的选择时间内有次日重复付费的用户需记录，每个用户单位为1 */
    private int otherRechargeNum;
    /** 对应区服的选择时间内的注册人数 */
    private int registerNum;
    /** 对应区服的选择时间内的创角数 */
    private int roleNum;
    /** 对应区服的选择时间内的创角率 */
    private String roleRate;
    /** 对应区服的选择时间内的活跃人数 */
    private int activeNum;
    /** 对应区服的选择时间内的老玩家活跃数，不可重复 */
    private int oldActiveNum;
    /** 从开服到当天对应区服的arppu[总充值金额/总注册人数] */
    private String arppu;
    /** 从开服到当天对应区服的arpu[总充值金额/总付费人数] */
    private String arpu;
    /** 消耗元宝 */
    private int costGold;

    public static RechargeInfo empty(int platform, int server) {
        RechargeInfo info = new RechargeInfo();
        info.platform = platform;
        info.server = server;
        return info;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public int getOpenDays() {
        return openDays;
    }

    public void setOpenDays(int openDays) {
        this.openDays = openDays;
    }

    public int getRmb() {
        return rmb;
    }

    public void setRmb(int rmb) {
        this.rmb = rmb;
    }

    public int getSumRmb() {
        return sumRmb;
    }

    public void setSumRmb(int sumRmb) {
        this.sumRmb = sumRmb;
    }

    public int getRechargeNum() {
        return rechargeNum;
    }

    public void setRechargeNum(int rechargeNum) {
        this.rechargeNum = rechargeNum;
    }

    public String getRechargeRate() {
        return rechargeRate;
    }

    public void setRechargeRate(String rechargeRate) {
        this.rechargeRate = rechargeRate;
    }

    public int getRepeatRechargeNum() {
        return repeatRechargeNum;
    }

    public void setRepeatRechargeNum(int repeatRechargeNum) {
        this.repeatRechargeNum = repeatRechargeNum;
    }

    public int getOtherRechargeNum() {
        return otherRechargeNum;
    }

    public void setOtherRechargeNum(int otherRechargeNum) {
        this.otherRechargeNum = otherRechargeNum;
    }

    public int getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(int registerNum) {
        this.registerNum = registerNum;
    }

    public int getRoleNum() {
        return roleNum;
    }

    public void setRoleNum(int roleNum) {
        this.roleNum = roleNum;
    }

    public String getRoleRate() {
        return roleRate;
    }

    public void setRoleRate(String roleRate) {
        this.roleRate = roleRate;
    }

    public int getActiveNum() {
        return activeNum;
    }

    public void setActiveNum(int activeNum) {
        this.activeNum = activeNum;
    }

    public int getOldActiveNum() {
        return oldActiveNum;
    }

    public void setOldActiveNum(int oldActiveNum) {
        this.oldActiveNum = oldActiveNum;
    }

    public String getArppu() {
        return arppu;
    }

    public void setArppu(String arppu) {
        this.arppu = arppu;
    }

    public String getArpu() {
        return arpu;
    }

    public void setArpu(String arpu) {
        this.arpu = arpu;
    }

    public int getCostGold() {
        return costGold;
    }

    public void setCostGold(int costGold) {
        this.costGold = costGold;
    }

    @Override
    public String toString() {
        return "RechargeInfo{" +
                "openDate='" + openDate + '\'' +
                ", openDays=" + openDays +
                ", rmb=" + rmb +
                ", sumRmb=" + sumRmb +
                ", rechargeNum=" + rechargeNum +
                ", rechargeRate='" + rechargeRate + '\'' +
                ", repeatRechargeNum=" + repeatRechargeNum +
                ", otherRechargeNum=" + otherRechargeNum +
                ", registerNum=" + registerNum +
                ", roleNum=" + roleNum +
                ", roleRate='" + roleRate + '\'' +
                ", activeNum=" + activeNum +
                ", oldActiveNum=" + oldActiveNum +
                ", arppu='" + arppu + '\'' +
                ", arpu='" + arpu + '\'' +
                ", costGold=" + costGold +
                '}';
    }

    @Override
    public String getPlatformServer() {
        if (firstColumnName == null) {
            return super.getPlatformServer();
        }
        return firstColumnName;
    }

    public void setFirstColumnName(String firstColumnName) {
        this.firstColumnName = firstColumnName;
    }
}
