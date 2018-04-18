package com.bigao.backend.module.player.dto;

import com.bigao.backend.common.CommonDto;

import java.util.List;

/**
 * Created by wait on 2016/1/20.
 */
public class PlayerLoginDto extends CommonDto {
    private List<PlayerLoginInfo> loginInfo;

    public List<PlayerLoginInfo> getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(List<PlayerLoginInfo> loginInfo) {
        this.loginInfo = loginInfo;
    }
}
