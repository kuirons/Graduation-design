package com.bigao.backend.module.register.dto;

import com.bigao.backend.common.CommonDto;

import java.util.List;

/**
 * Created by wait on 2015/12/1.
 */
public class RegisterDto extends CommonDto {
    private List<RegisterInfo> registerInfo;

    public List<RegisterInfo> getRegisterInfo() {
        return registerInfo;
    }

    public RegisterDto setRegisterInfo(List<RegisterInfo> registerInfo) {
        this.registerInfo = registerInfo;
        return this;
    }
}
