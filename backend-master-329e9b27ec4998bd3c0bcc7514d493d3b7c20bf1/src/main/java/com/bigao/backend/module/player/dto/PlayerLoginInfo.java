package com.bigao.backend.module.player.dto;

/**
 * Created by wait on 2016/1/20.
 */
public class PlayerLoginInfo {
    private String state;
    private String time;
    private String ip;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
