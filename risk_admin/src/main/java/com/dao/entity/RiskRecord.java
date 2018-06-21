package com.dao.entity;

import java.util.Date;

public class RiskRecord {
	
	private Integer rowId; //　数据库查询专用
	private Integer id;
	private String pid;
	private String tradeNo;
	private String riskType;
	private String riskLevel;
	private String riskStatus;
	private Date createTime;
	private Date updateTime;
	
	public Integer getRowId() {
		return rowId;
	}
	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getRiskType() {
		return riskType;
	}
	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}
	public String getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	public String getRiskStatus() {
		return riskStatus;
	}
	public void setRiskStatus(String riskStatus) {
		this.riskStatus = riskStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreatTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "RiskRecord [id=" + id + ", pid=" + pid + ", tradeNo=" + tradeNo + ", riskType=" + riskType
				+ ", riskLevel=" + riskLevel + ", riskStatus=" + riskStatus + ", createTime=" + createTime + ", updateTime="
				+ updateTime + "]";
	}
	
}
