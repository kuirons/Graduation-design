package com.bigao.backend.module.player.dto;
/**
 * Created by wait on 2016/28/4.
 */
public class PlayerMonetaryInfo {
	/** 行为*/
	private String action;
	/** 变化数量*/
    private int change;
    /** 变化之后的数量*/
    private int newNum;       
    /** 变化之前的数量*/
    private int oldNum;
    /** 时间*/
    private String time;
    
    public PlayerMonetaryInfo(String action, int change, int newNum, int oldNum, String time) {
		super();
		this.action = action;
		this.change = change;
		this.newNum = newNum;
		this.oldNum = oldNum;
		this.time = time;
	}
	public PlayerMonetaryInfo() {
		super();
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getChange() {
		return change;
	}
	public void setChange(int change) {
		this.change = change;
	}
	public int getNewNum() {
		return newNum;
	}
	public void setNewNum(int newNum) {
		this.newNum = newNum;
	}
	public int getOldNum() {
		return oldNum;
	}
	public void setOldNum(int oldNum) {
		this.oldNum = oldNum;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
    
}
