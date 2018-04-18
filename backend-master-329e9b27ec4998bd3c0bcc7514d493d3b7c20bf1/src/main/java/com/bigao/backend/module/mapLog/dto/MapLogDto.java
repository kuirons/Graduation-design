package com.bigao.backend.module.mapLog.dto;

public class MapLogDto{
	/**参与人数 */
	private int roleNum;
	/**可以参与人数 */
	private int sureNum;
	/**参与率 */
	private String involvRate;
	/**平均参与时间 */
	private long avgTime;
	
	public int getRoleNum() {
		return roleNum;
	}
	public void setRoleNum(int roleNum) {
		this.roleNum = roleNum;
	}
	public int getSureNum() {
		return sureNum;
	}
	public void setSureNum(int sureNum) {
		this.sureNum = sureNum;
	}
	public String getInvolvRate() {
		return involvRate;
	}
	public void setInvolvRate(String involvRate) {
		this.involvRate = involvRate;
	}
	public long getAvgTime() {
		return avgTime;
	}
	public void setAvgTime(long avgTime) {
		this.avgTime = avgTime;
	}
	
	@Override
	public String toString() {
		return "MapLogInfo [roleNum=" + roleNum + ", involvRate=" + involvRate + ", avgTime=" + avgTime + "]";
	}
	
}
