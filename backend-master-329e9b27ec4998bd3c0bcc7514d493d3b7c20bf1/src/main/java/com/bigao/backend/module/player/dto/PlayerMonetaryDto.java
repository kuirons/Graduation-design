package com.bigao.backend.module.player.dto;

import java.util.List;

import com.bigao.backend.common.CommonDto;

public class PlayerMonetaryDto extends CommonDto{
	private List<PlayerMonetaryInfo> infos;

	public List<PlayerMonetaryInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<PlayerMonetaryInfo> infos) {
		this.infos = infos;
	}
	
}
