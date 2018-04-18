package com.bigao.backend.module.recharge.dto;

import java.lang.reflect.Field;

/**
 * 最后一行的总结
 * Created by wait on 2016/1/9.
 */
public class RechargeSumInfo {

    private String name = "总计";

    /** 开服天数 */
    private int openDays;
    /** 充值RMB */
    private int rmb;
    /** 总充值RMB */
    private int sumRmb;
    /** 充值人数 */
    private int rechargeNum;
    /** 充值占比 */
    private String rechargeRate;
    /** 用户重复付费次数 */
    private int repeatRechargeNum;
    /** 用户次日重复付费次数 */
    private int otherRechargeNum;
    /** 注册人数 */
    private int registerNum;
    /** 创角数 */
    private int roleNum;
    /** 创角率 */
    private String roleRate;
    /** 活跃人数 */
    private int activeNum;
    /** 老玩家活跃数 */
    private int oldActiveNum;
    /** arppu */
    private String arppu;
    /** arpu */
    private String arpu;
    /** 消耗元宝 */
    private int costGold;

    public static void main(String[] args) {
        Field[] all = RechargeSumInfo.class.getDeclaredFields();
        for (Field f : all) {
            String fName = f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
            System.err.println("sumInfo.set" + fName + "(sumInfo.get" + fName + "() + info.get" + fName + "());");
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
