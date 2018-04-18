package com.bigao.backend.module.player.dto;

import java.util.List;

import com.bigao.backend.common.CommonDto;

public class PlayerBagDto extends CommonDto{
	private List<PlayerBagInfo> playerBagInfos;

	public List<PlayerBagInfo> getPlayerBagInfos() {
		return playerBagInfos;
	}

	public void setPlayerBagInfos(List<PlayerBagInfo> playerBagInfos) {
		this.playerBagInfos = playerBagInfos;
	}
	
}
