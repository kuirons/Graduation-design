package com.bigao.backend.module.zone.dto;

/**
 * Created by wait on 2015/12/1.
 */
public class CopyInfo {
    private int copyId;
    private String copyName;
    private int lostNum;
    private String lostRate;

    public int getCopyId() {
        return copyId;
    }

    public CopyInfo setCopyId(int copyId) {
        this.copyId = copyId;
        return this;
    }

    public String getCopyName() {
        return copyName;
    }

    public CopyInfo setCopyName(String copyName) {
        this.copyName = copyName;
        return this;
    }

    public int getLostNum() {
        return lostNum;
    }

    public CopyInfo setLostNum(int lostNum) {
        this.lostNum = lostNum;
        return this;
    }

    public String getLostRate() {
        return lostRate;
    }

    public void setLostRate(String lostRate) {
        this.lostRate = lostRate;
    }


    @Override
    public String toString() {
        return "CopyInfo{" +
                "copyId=" + copyId +
                ", copyName='" + copyName + '\'' +
                ", lostNum=" + lostNum +
                ", lostRate='" + lostRate + '\'' +
                '}';
    }
}
