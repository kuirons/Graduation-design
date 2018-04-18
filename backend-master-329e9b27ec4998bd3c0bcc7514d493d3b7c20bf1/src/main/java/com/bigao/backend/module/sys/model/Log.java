package com.bigao.backend.module.sys.model;

public class Log {
    private int id;
    private String username;
    private String content;
    private String ip;
    private long creatTime;

    public Log() {
    }

    public Log(String username, String content, String ip, long creatTime) {
        super();
        this.username = username;
        this.content = content;
        this.ip = ip;
        this.creatTime = creatTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(long creatTime) {
        this.creatTime = creatTime;
    }


}
