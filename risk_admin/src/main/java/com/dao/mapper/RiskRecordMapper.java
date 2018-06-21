package com.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dao.entity.RiskRecord;
import com.dao.entity.RiskRecordBack;

public interface RiskRecordMapper {
	
	
	
	/******************************************************risk_record
	 * 单条件查询
	 */
	public RiskRecord getRiskRecordByTradeNo(String tradeNo);
	
	public RiskRecord getRiskRecordById(Integer id);
	
	/**
	 * 新增
	 */
	public int addRiskRecord(RiskRecord riskRecord);
	
	/**
	 * 查询所有
	 */
	public List<RiskRecord> getRiskRecordAll();
	
	/**
	 * 关联信息查询
	 */
	public List<Map<String, Object>> findRiskInfo(Map<String, Object> params);
	public Map<String, Object> findRiskInfoById(Integer id);
	
	/**
	 * 删除
	 * 
	 */
	public int deleteRiskRecordById(Integer id);
	/**
	 * 关闭状态
	 * 
	 */
	public int updateRiskRecordStatusById(@Param("riskStatus") String riskStatus, @Param("id") Integer id);
	
	/*************************************************************risk_record_back
	 * 新增
	 */
	public int addRiskRecordBack(RiskRecordBack riskRecordBack);
	/**
	 * 删除
	 */
	public int deleteRiskRecordBackByRecordId(Integer recordId);
	/**
	 * 根据record_id查询
	 */
	public RiskRecordBack getRiskRecordBackByRecordId(Integer recordId);
	
}
