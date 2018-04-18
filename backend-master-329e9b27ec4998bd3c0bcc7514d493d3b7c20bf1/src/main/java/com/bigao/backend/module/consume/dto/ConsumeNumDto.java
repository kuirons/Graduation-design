package com.bigao.backend.module.consume.dto;

import com.bigao.backend.common.CommonDto;

import java.util.List;

/**
 * Created by wait on 2015/12/13.
 */
public class ConsumeNumDto extends CommonDto {

    private List<ConsumeNumInfo> numInfo;

    public ConsumeNumDto() {
    }

    public ConsumeNumDto(List<ConsumeNumInfo> numInfo) {
        this.numInfo = numInfo;
    }

    public List<ConsumeNumInfo> getNumInfo() {
        return numInfo;
    }

    public ConsumeNumDto setNumInfo(List<ConsumeNumInfo> numInfo) {
        this.numInfo = numInfo;
        return this;
    }
}
