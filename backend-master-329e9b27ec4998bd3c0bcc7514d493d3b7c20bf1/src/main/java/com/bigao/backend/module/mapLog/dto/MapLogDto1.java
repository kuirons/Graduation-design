package com.bigao.backend.module.mapLog.dto;

import java.util.List;

import com.bigao.backend.common.CommonDto;

public class MapLogDto1 extends CommonDto{
	private List<MapLogDto> lists;

	public List<MapLogDto> getLists() {
		return lists;
	}

	public void setLists(List<MapLogDto> lists) {
		this.lists = lists;
	}
	
	
}
