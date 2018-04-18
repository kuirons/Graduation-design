package com.bigao.backend.module.zone;

/**
 * Created by wait on 2015/12/28.
 */
public class CopyTypeInfo {
    /** 副本ID */
    private int value;
    /** 副本名字 */
    private String name;

    public static CopyTypeInfo valueOf(int value, String name) {
        return new CopyTypeInfo().setValue(value).setName(name);
    }

    public int getValue() {
        return value;
    }

    public CopyTypeInfo setValue(int value) {
        this.value = value;
        return this;
    }

    public String getName() {
        return name;
    }

    public CopyTypeInfo setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "CopyTypeInfo{" +
                "value=" + value +
                ", name='" + name + '\'' +
                '}';
    }
}
