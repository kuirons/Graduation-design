package com.bigao.backend.module.gm.dto;

import com.bigao.backend.common.CommonDto;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by wait on 2015/12/26.
 */
public class GmLogDto extends CommonDto {
    private List<GmLogInfo> gmInfo = Lists.newArrayList();

    public List<GmLogInfo> getGmInfo() {
        return gmInfo;
    }

    public GmLogDto setGmInfo(List<GmLogInfo> gmInfo) {
        this.gmInfo = gmInfo;
        return this;
    }
}
