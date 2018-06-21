package com.utils;

public enum RiskRecordStatus {
		
	wait_deal("待处理"), 
	
	dealed("已处理"), 
	
	off("已关闭");
	
	private String desc;
	
	private RiskRecordStatus(String desc) {
		this.desc = desc;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
}
