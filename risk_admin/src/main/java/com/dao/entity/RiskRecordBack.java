package com.dao.entity;

import java.util.Date;

public class RiskRecordBack {
	
	private Integer id;
	private Integer recordId;
	private String bankCardNo;
	private String certNo;
	private String businessLicenseNo;
	private String mobile;
	private String mobileIp;
	private String orderIp;
	private String merchName;
	private String emailAddress;
	private String processCode;
	private Date createTime;
	private Date updateTime;
	private Integer operateUser;
	
	private String retCode;
	private String retMsg;
	
	private String tradeNo;
	private String pid;
	private String merchantNo;
	
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getBusinessLicenseNo() {
		return businessLicenseNo;
	}
	public void setBusinessLicenseNo(String businessLicenseNo) {
		this.businessLicenseNo = businessLicenseNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMobileIp() {
		return mobileIp;
	}
	public void setMobileIp(String mobileIp) {
		this.mobileIp = mobileIp;
	}
	public String getOrderIp() {
		return orderIp;
	}
	public void setOrderIp(String orderIp) {
		this.orderIp = orderIp;
	}
	public String getMerchName() {
		return merchName;
	}
	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getOperateUser() {
		return operateUser;
	}
	public void setOperateUser(Integer operateUser) {
		this.operateUser = operateUser;
	}
	@Override
	public String toString() {
		return "RiskRecordBack [id=" + id + ", recordId=" + recordId + ", bankCardNo=" + bankCardNo + ", certNo="
				+ certNo + ", businessLicenseNo=" + businessLicenseNo + ", mobile=" + mobile + ", mobileIp=" + mobileIp
				+ ", orderIp=" + orderIp + ", merchName=" + merchName + ", emailAddress=" + emailAddress
				+ ", processCode=" + processCode + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", operateUser=" + operateUser + ", retCode=" + retCode + ", retMsg=" + retMsg 
				+ ", tradeNo=" + tradeNo + ", pid=" + pid + ", merchantNo=" + merchantNo + "]";
	}
	
}
