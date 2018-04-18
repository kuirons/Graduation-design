package com.bigao.backend.module.commonGm.dto;

import com.bigao.backend.common.CommonDto;

/**
 * Created by wait on 2016/1/4.
 */
public class OneMailDto extends CommonDto {
    private String result;
    private String content;

    public static OneMailDto err(String content) {
        OneMailDto dto = new OneMailDto();
        dto.result = Boolean.FALSE.toString();
        dto.message = content;
        return dto;
    }

    public static OneMailDto succ(String content) {
        OneMailDto dto = new OneMailDto();
        dto.result = Boolean.TRUE.toString();
        dto.content = content;
        return dto;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
