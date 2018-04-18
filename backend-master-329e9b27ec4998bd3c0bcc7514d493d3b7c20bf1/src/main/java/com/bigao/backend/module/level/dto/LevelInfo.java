package com.bigao.backend.module.level.dto;

/**
 * Created by wait on 2015/12/14.
 */
public class LevelInfo {
    /** 等级 */
    private int level;
    /** 数量 */
    private int num;

    public LevelInfo(int level, int num) {
        this.level = level;
        this.num = num;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "LevelInfo{" +
                "level=" + level +
                ", num=" + num +
                '}';
    }
}
