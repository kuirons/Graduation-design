package com.bigao.backend.module.gm.dto;

import com.bigao.backend.common.PlatformKey;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * Created by wait on 2015/12/26.
 */
public class GmResultDto {
    private String result;

    private List<GmResultInfo> successList = Lists.newArrayList();
    private List<GmResultInfo> failList = Lists.newArrayList();

    public static GmResultDto build(Map<PlatformKey, GmResultInfo> data) {
        GmResultDto dto = valueOf().setResult(Boolean.TRUE.toString());
        for (Map.Entry<PlatformKey, GmResultInfo> e : data.entrySet()) {
            if (e.getValue().isFail()) {
                dto.getFailList().add(e.getValue());
            } else {
                dto.getSuccessList().add(e.getValue());
            }
        }
        return dto;
    }

    public static GmResultDto valueOf() {
        return new GmResultDto();
    }

    public String getResult() {
        return result;
    }

    public GmResultDto setResult(String result) {
        this.result = result;
        return this;
    }

    public List<GmResultInfo> getSuccessList() {
        return successList;
    }

    public GmResultDto setSuccessList(List<GmResultInfo> successList) {
        this.successList = successList;
        return this;
    }

    public List<GmResultInfo> getFailList() {
        return failList;
    }

    public GmResultDto setFailList(List<GmResultInfo> failList) {
        this.failList = failList;
        return this;
    }
}
