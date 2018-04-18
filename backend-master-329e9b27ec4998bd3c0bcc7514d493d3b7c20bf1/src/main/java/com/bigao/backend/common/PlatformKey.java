package com.bigao.backend.common;

/**
 * Created by wait on 2015/11/30.
 */
public class PlatformKey {
    private final int platformId;
    private final int serverId;

    public PlatformKey(int platformId, int serverId) {
        this.platformId = platformId;
        this.serverId = serverId;
    }

    public static PlatformKey valueOf(int platformId, int serverId) {
        return new PlatformKey(platformId, serverId);
    }

    public int getPlatformId() {
        return platformId;
    }

    public int getServerId() {
        return serverId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlatformKey key = (PlatformKey) o;

        if (platformId != key.platformId) return false;
        return serverId == key.serverId;

    }

    @Override
    public int hashCode() {
        int result = platformId;
        result = 31 * result + serverId;
        return result;
    }

    @Override
    public String toString() {
        return "PlatformKey{" +
                "platformId=" + platformId +
                ", serverId=" + serverId +
                '}';
    }
}
