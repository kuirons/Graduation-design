package com.bigao.backend.db;

/**
 * 平台
 * Created by wait on 2015/12/29.
 */
public class Platform {
    private String name;
    private int value;
    private String token;
    private String note;

    public static Platform valueOf(String name, int value) {
        Platform p = new Platform();
        p.name = name;
        p.value = value;
        return p;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
