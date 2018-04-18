package com.bigao.backend.module.online.dto;

import com.bigao.backend.common.CommonDto;

import java.util.List;

/**
 * Created by wait on 2015/12/1.
 */
public class OnlineTopDto extends CommonDto {
    private int type;
    private List<OnlineTopInfo> topInfo;

    public int getType() {
        return type;
    }

    public OnlineTopDto setType(int type) {
        this.type = type;
        return this;
    }

    public List<OnlineTopInfo> getTopInfo() {
        return topInfo;
    }

    public OnlineTopDto setTopInfo(List<OnlineTopInfo> topInfo) {
        this.topInfo = topInfo;
        return this;
    }
}
