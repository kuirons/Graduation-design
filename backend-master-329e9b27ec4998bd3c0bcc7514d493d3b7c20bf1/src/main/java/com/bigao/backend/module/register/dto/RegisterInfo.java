package com.bigao.backend.module.register.dto;

import com.bigao.backend.module.common.PlatformServerInfo;

/**
 * Created by wait on 2015/12/1.
 */
public class RegisterInfo extends PlatformServerInfo {
    /** 注册人数 */
    private int registerNum;
    /** 创角数 */
    private int createRoleNum;
    /** 创建率 */
    private String createRate = "0.00%";


    public int getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(int registerNum) {
        this.registerNum = registerNum;
    }

    public int getCreateRoleNum() {
        return createRoleNum;
    }

    public void setCreateRoleNum(int createRoleNum) {
        this.createRoleNum = createRoleNum;
    }

    public String getCreateRate() {
        return createRate;
    }

    public void setCreateRate(String createRate) {
        this.createRate = createRate;
    }

    @Override
    public String toString() {
        return "RegisterInfo{" +
                "registerNum=" + registerNum +
                ", createRoleNum=" + createRoleNum +
                ", createRate='" + createRate + '\'' +
                '}';
    }
}
