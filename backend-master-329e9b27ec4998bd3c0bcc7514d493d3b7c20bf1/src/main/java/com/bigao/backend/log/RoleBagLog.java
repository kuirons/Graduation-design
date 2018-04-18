package com.bigao.backend.log;

import java.sql.Timestamp;

import com.bigao.backend.common.anno.TableDesc;

/**
 * 角色背包日志
 * @author Administrator
 *
 */
@TableDesc(value="rolebaglog")
public class RoleBagLog {
	/** 行为 */
	private String action;
	/** 背包信息 */
	private String bagInfo;
	/** 背包信息 */
	private String bagType;
	/** 道具id */ 
	private int itemId;
	/** 道具数量 */		 
	private int itemNum;         
	/** 参数说明 */
	private String opt;      
	/** 平台 */
	private int platform;        
	/** 角色ID */
	private long roleId;        
	/** 序列号 */
	private long serialNumber;
	/** 服务器 */
	private int server;       
	/** 背包信息 */
	private Timestamp time;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getBagInfo() {
		return bagInfo;
	}
	public void setBagInfo(String bagInfo) {
		this.bagInfo = bagInfo;
	}
	public String getBagType() {
		return bagType;
	}
	public void setBagType(String bagType) {
		this.bagType = bagType;
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
	public int getPlatform() {
		return platform;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public long getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}
	public int getServer() {
		return server;
	}
	public void setServer(int server) {
		this.server = server;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
}
