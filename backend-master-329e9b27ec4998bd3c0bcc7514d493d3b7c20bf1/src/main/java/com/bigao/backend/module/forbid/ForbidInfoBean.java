package com.bigao.backend.module.forbid;

/**
 * 封号信息
 * Created by wait on 2016/1/18.
 */
public class ForbidInfoBean {
    public static final byte BY_IP = 0;
    public static final byte BY_ACCOUNT = 1;

    private int id;
    private String content;
    /** 封号类型[0:ip 1:账号] */
    private byte forbidType;

    public static ForbidInfoBean valueOf(String content, byte forbidType) {
        ForbidInfoBean bean = new ForbidInfoBean();
        bean.content = content;
        bean.forbidType = forbidType;
        return bean;
    }

    public static ForbidInfoBean valueOf(int id, String content, byte forbidType) {
        ForbidInfoBean bean = new ForbidInfoBean();
        bean.id = id;
        bean.content = content;
        bean.forbidType = forbidType;
        return bean;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte getForbidType() {
        return forbidType;
    }

    public void setForbidType(byte forbidType) {
        this.forbidType = forbidType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}