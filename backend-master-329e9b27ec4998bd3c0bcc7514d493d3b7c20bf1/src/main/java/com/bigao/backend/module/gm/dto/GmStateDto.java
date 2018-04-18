package com.bigao.backend.module.gm.dto;

/**
 * Created by wait on 2015/12/26.
 */
public class GmStateDto {
    private String result;
    private String percentage;

    public static GmStateDto err(String percentage) {
        GmStateDto dto = new GmStateDto();
        dto.result = Boolean.FALSE.toString();
        dto.percentage = percentage;
        return dto;
    }

    public static GmStateDto succ(String percentage) {
        GmStateDto dto = new GmStateDto();
        dto.result = Boolean.TRUE.toString();
        dto.percentage = percentage;
        return dto;
    }

    public String getResult() {
        return result;
    }

    public GmStateDto setResult(String result) {
        this.result = result;
        return this;
    }

    public String getPercentage() {
        return percentage;
    }

    public GmStateDto setPercentage(String percentage) {
        this.percentage = percentage;
        return this;
    }
}
