package com.bigao.backend.module.online.dto;

/**
 * Created by wait on 2015/12/1.
 */
public class OnlineRealTimeInfo {
    private String nowTime;
    private int onlineNum;

    public static OnlineRealTimeInfo valueOf(String nowTime, int onlineNum) {
        OnlineRealTimeInfo info = new OnlineRealTimeInfo();
        info.nowTime = nowTime;
        info.onlineNum = onlineNum;
        return info;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public int getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(int onlineNum) {
        this.onlineNum = onlineNum;
    }


    @Override
    public String toString() {
        return "OnlineRealTimeInfo{" +
                "nowTime='" + nowTime + '\'' +
                ", onlineNum=" + onlineNum +
                '}';
    }
}
