package com.bigao.backend.module.online.dto;

import com.bigao.backend.common.CommonDto;

import java.util.List;

/**
 * Created by wait on 2015/12/1.
 */
public class OnlineTimeDto extends CommonDto {
    private int type;
    private List<OnlineTimeInfo> onlineTimeInfo;

    public int getType() {
        return type;
    }

    public OnlineTimeDto setType(int type) {
        this.type = type;
        return this;
    }

    public List<OnlineTimeInfo> getOnlineTimeInfo() {
        return onlineTimeInfo;
    }

    public OnlineTimeDto setOnlineTimeInfo(List<OnlineTimeInfo> onlineTimeInfo) {
        this.onlineTimeInfo = onlineTimeInfo;
        return this;
    }
}
