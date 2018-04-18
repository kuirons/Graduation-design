package com.bigao.backend.module.online.dto;

import com.bigao.backend.common.CommonDto;

import java.util.List;

/**
 * Created by wait on 2015/12/1.
 */
public class OnlineRealTimeDto extends CommonDto {
    private List<OnlineRealTimeInfo> realTimeInfo;

    public List<OnlineRealTimeInfo> getRealTimeInfo() {
        return realTimeInfo;
    }

    public OnlineRealTimeDto setRealTimeInfo(List<OnlineRealTimeInfo> realTimeInfo) {
        this.realTimeInfo = realTimeInfo;
        return this;
    }
}
