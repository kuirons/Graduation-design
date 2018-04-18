package com.bigao.backend.module.online.dto;

import com.bigao.backend.module.common.PlatformServerInfo;

/**
 * Created by wait on 2015/12/1.
 */
public class OnlineHistoryInfo extends PlatformServerInfo {
    private String time;
    private int onlineNum;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(int onlineNum) {
        this.onlineNum = onlineNum;
    }
}
