package com.bigao.backend.module.player.dto;

/**
 * 角色背包类
 * @author Administrator
 *
 */
public class PlayerBagInfo {
	/** 行为 */
	private String action;
	/** 道具id */ 
	private int itemId;
	/** 道具数量 */		 
	private int itemNum;         
	/** 参数说明 */
	private String opt;              
	/** 时间 */
	private String time;
	
	public PlayerBagInfo() {
		
	}
	
	public PlayerBagInfo(String action, int itemId, int itemNum, String opt, String time) {
		this.action = action;
		this.itemId = itemId;
		this.itemNum = itemNum;
		this.opt = opt;
		this.time = time;
	}

	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getItemNum() {
		return itemNum;
	}
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	public String getOpt() {
		return opt;
	}
	public void setOpt(String opt) {
		this.opt = opt;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
     
}
