package com.bigao.backend.common;

/**
 * Created by wait on 2015/12/10.
 */
public class RoleIDKey {
    private final int platform;
    private final int server;
    private final long roleId;

    public RoleIDKey(int platform, int server, long roleId) {
        this.platform = platform;
        this.server = server;
        this.roleId = roleId;
    }

    public int getPlatform() {
        return platform;
    }

    public int getServer() {
        return server;
    }

    public long getRoleId() {
        return roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleIDKey roleIDKey = (RoleIDKey) o;

        if (platform != roleIDKey.platform) return false;
        if (server != roleIDKey.server) return false;
        return roleId == roleIDKey.roleId;

    }

    @Override
    public int hashCode() {
        int result = platform;
        result = 31 * result + server;
        result = 31 * result + (int) (roleId ^ (roleId >>> 32));
        return result;
    }
}
