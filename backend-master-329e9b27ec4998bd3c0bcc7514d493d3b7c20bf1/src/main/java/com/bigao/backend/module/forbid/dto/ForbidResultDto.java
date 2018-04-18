package com.bigao.backend.module.forbid.dto;

import com.bigao.backend.common.CommonResult;

/**
 * Created by wait on 2016/1/18.
 */
public class ForbidResultDto {
    private int result;
    private String content;

    public static ForbidResultDto valueOf(int result, String content) {
        ForbidResultDto dto = new ForbidResultDto();
        dto.result = result;
        dto.content = content;
        return dto;
    }

    public static ForbidResultDto valueOf(CommonResult commonResult) {
        ForbidResultDto dto = new ForbidResultDto();
        dto.result = commonResult.getResult();
        dto.content = commonResult.getContent();
        return dto;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
