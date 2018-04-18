package com.bigao.backend.module.player.dto;

/**
 * Created by wait on 2015/12/10.
 */
public class RoleLevelInfo {
    private int level;
    private String createTime;

    public int getLevel() {
        return level;
    }

    public RoleLevelInfo setLevel(int level) {
        this.level = level;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public RoleLevelInfo setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }
}
