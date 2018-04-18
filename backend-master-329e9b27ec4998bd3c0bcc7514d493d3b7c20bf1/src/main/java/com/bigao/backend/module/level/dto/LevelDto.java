package com.bigao.backend.module.level.dto;

import com.bigao.backend.common.CommonDto;

import java.util.List;

/**
 * Created by wait on 2015/12/14.
 */
public class LevelDto extends CommonDto {
    private List<LevelInfo> levelInfo;
    /**等级总人数*/
    private int allLevelNum;
    
    public LevelDto() {
    }

    public LevelDto(List<LevelInfo> levelInfo, int allLevelNum) {
        this.levelInfo = levelInfo;
        this.allLevelNum = allLevelNum;
    }

    public List<LevelInfo> getLevelInfo() {
        return levelInfo;
    }

    public void setLevelInfo(List<LevelInfo> levelInfo) {
        this.levelInfo = levelInfo;
    }

	public int getAllLevelNum() {
		return allLevelNum;
	}

	public void setAllLevelNum(int allLevelNum) {
		this.allLevelNum = allLevelNum;
	}
    
}
