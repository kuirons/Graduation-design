package com.bigao.backend.module.gm.dto;

import com.bigao.backend.module.gm.AsyncKey;

/**
 * Created by wait on 2015/12/26.
 */
public class GmCommandDto {
    private String batchSign;
    private int asyncKey;
    private String errorMessage = "";

    public static GmCommandDto err(String errorMessage, AsyncKey asyncKey) {
        GmCommandDto dto = new GmCommandDto();
        dto.errorMessage = errorMessage;
        dto.asyncKey = asyncKey.getKey();
        return dto;
    }

    public static GmCommandDto succ(String batchSign, AsyncKey asyncKey) {
        GmCommandDto dto = new GmCommandDto();
        dto.batchSign = batchSign;
        dto.asyncKey = asyncKey.getKey();
        return dto;
    }

    public String getBatchSign() {
        return batchSign;
    }

    public void setBatchSign(String batchSign) {
        this.batchSign = batchSign;
    }

    public int getAsyncKey() {
        return asyncKey;
    }

    public void setAsyncKey(int asyncKey) {
        this.asyncKey = asyncKey;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
