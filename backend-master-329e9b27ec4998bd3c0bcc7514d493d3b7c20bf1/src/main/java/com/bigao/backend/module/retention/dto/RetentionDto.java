package com.bigao.backend.module.retention.dto;

import com.bigao.backend.common.CommonDto;

import java.util.List;

/**
 * Created by wait on 2015/12/1.
 */
public class RetentionDto extends CommonDto {
    private List<RetentionInfo> saveInfo;
    /** 开服日期 */
    private String openServerDate;
    /** 服务器 */
    private String platformServer;

    public List<RetentionInfo> getSaveInfo() {
        return saveInfo;
    }

    public RetentionDto setSaveInfo(List<RetentionInfo> saveInfo) {
        this.saveInfo = saveInfo;
        return this;
    }

    public String getOpenServerDate() {
        return openServerDate;
    }

    public RetentionDto setOpenServerDate(String openServerDate) {
        this.openServerDate = openServerDate;
        return this;
    }

    public String getPlatformServer() {
        return platformServer;
    }

    public RetentionDto setPlatformServer(String platformServer) {
        this.platformServer = platformServer;
        return this;
    }
}
