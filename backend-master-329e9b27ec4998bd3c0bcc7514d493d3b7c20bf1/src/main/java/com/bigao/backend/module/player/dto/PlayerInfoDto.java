package com.bigao.backend.module.player.dto;

import com.bigao.backend.common.CommonDto;

import java.util.List;

/**
 * Created by wait on 2016/1/4.
 */
public class PlayerInfoDto extends CommonDto {
    private List<PlayerInfo> playerInfo;

    public List<PlayerInfo> getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(List<PlayerInfo> playerInfo) {
        this.playerInfo = playerInfo;
    }
}
