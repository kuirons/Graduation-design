package com.bigao.backend.common;

/**
 * 注意:继承这个类主要用于通过反射设置message, 当含有带参构造函数时, 需要手动添加一个无参构造函数
 * Created by wait on 2015/12/1.
 */
public abstract class CommonDto {

    protected String message = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}