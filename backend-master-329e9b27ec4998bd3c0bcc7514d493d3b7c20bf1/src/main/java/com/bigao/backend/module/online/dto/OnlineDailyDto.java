package com.bigao.backend.module.online.dto;

import com.bigao.backend.common.CommonDto;

import java.util.List;

/**
 * Created by wait on 2015/12/1.
 */
public class OnlineDailyDto extends CommonDto {
    private List<OnlineDailyInfo> dailyInfo;

    public List<OnlineDailyInfo> getDailyInfo() {
        return dailyInfo;
    }

    public OnlineDailyDto setDailyInfo(List<OnlineDailyInfo> dailyInfo) {
        this.dailyInfo = dailyInfo;
        return this;
    }
}
