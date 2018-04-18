package com.bigao.backend.module.mapLog.dto;

public class MapLogInfo {
	private String opt;
	private long differTime;
	
	public String getOpt() {
		return opt;
	}
	public void setOpt(String opt) {
		this.opt = opt;
	}
	public long getDifferTime() {
		return differTime;
	}
	public void setDifferTime(long differTime) {
		this.differTime = differTime;
	}
	@Override
	public String toString() {
		return "MapLogInfo [opt=" + opt + ", differTime=" + differTime + "]";
	}
	
	
}
