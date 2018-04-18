package com.bigao.backend.module.online.dto;

import com.bigao.backend.module.common.PlatformServerInfo;

/**
 * Created by wait on 2015/12/1.
 */
public class OnlineDailyInfo extends PlatformServerInfo {

    private int averageTime;

    public int getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(int averageTime) {
        this.averageTime = averageTime;
    }
}
