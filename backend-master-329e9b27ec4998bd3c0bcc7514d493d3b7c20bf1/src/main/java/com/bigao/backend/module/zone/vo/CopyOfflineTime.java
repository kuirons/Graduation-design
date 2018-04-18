package com.bigao.backend.module.zone.vo;

/**
 * Created by wait on 2016/2/24.
 */
public class CopyOfflineTime {
    private long offlineTime;
    private String copyType;

    public static CopyOfflineTime valueOf(long offlineTime, String copyType) {
        CopyOfflineTime copyOfflineTime = new CopyOfflineTime();
        copyOfflineTime.copyType = copyType;
        copyOfflineTime.offlineTime = offlineTime;
        return copyOfflineTime;
    }

    public long getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(long offlineTime) {
        this.offlineTime = offlineTime;
    }

    public String getCopyType() {
        return copyType;
    }

    public void setCopyType(String copyType) {
        this.copyType = copyType;
    }

    @Override
    public String toString() {
        return "CopyOfflineTime{" +
                "offlineTime=" + offlineTime +
                ", copyType='" + copyType + '\'' +
                '}';
    }
}
