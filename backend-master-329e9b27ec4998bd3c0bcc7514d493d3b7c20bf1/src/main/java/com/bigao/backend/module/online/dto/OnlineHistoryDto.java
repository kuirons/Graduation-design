package com.bigao.backend.module.online.dto;

import com.bigao.backend.common.CommonDto;

import java.util.List;

/**
 * Created by wait on 2015/12/1.
 */
public class OnlineHistoryDto extends CommonDto {
    private int type;
    private List<OnlineHistoryInfo> historyInfo;

    public int getType() {
        return type;
    }

    public OnlineHistoryDto setType(int type) {
        this.type = type;
        return this;
    }

    public List<OnlineHistoryInfo> getHistoryInfo() {
        return historyInfo;
    }

    public OnlineHistoryDto setHistoryInfo(List<OnlineHistoryInfo> historyInfo) {
        this.historyInfo = historyInfo;
        return this;
    }
}
