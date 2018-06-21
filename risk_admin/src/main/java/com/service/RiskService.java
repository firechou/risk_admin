package com.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.entity.RiskRecord;
import com.dao.entity.RiskRecordBack;
import com.dao.mapper.RiskRecordMapper;
import com.utils.RiskRecordStatus;
import com.utils.SKResult;

@Service
public class RiskService {
	
	@Autowired
	private RiskRecordMapper riskRecordMapper;
	
	/**
	 * 删除risk_record risk_record_back
	 * @param id
	 * @return
	 */
	@Transactional
	public SKResult deleteRisk(Integer id) {
		
		RiskRecord riskRecord = riskRecordMapper.getRiskRecordById(id);
		if(riskRecord==null) {
			return new SKResult(false, "该风险记录不存在");
		}
		
		// 删除risk_record
		int num1 = riskRecordMapper.deleteRiskRecordById(id);
		// 删除risk_record_back
		int num2 = riskRecordMapper.deleteRiskRecordBackByRecordId(id); // 可能没有记录
		if(num1<1 || num2<0) {
			return new SKResult(false, "系统异常");
		}
		
		return new SKResult(true, "删除成功");
	}
	
	/**
	 * 关闭风险记录
	 * @param id
	 * @return
	 */
	public SKResult offRiskStatus(Integer id) {
		
		RiskRecord riskRecord = riskRecordMapper.getRiskRecordById(id);
		if(riskRecord==null) {
			return new SKResult(false, "该风险记录不存在");
		}
		if(!RiskRecordStatus.wait_deal.toString().equals(riskRecord.getRiskStatus())) {
			return new SKResult(false, "该状态不许修改");
		}
		
		int num = riskRecordMapper.updateRiskRecordStatusById("off", id);
		if(num<=0) {
			return new SKResult(false, "系统异常");
		}
		
		return new SKResult(true, "关闭成功");
	}
	
	/**
	 * 增加处理信息
	 * @param riskRecordBack
	 * @return
	 */
	@Transactional
	public SKResult addRiskRecordBack(RiskRecordBack riskRecordBack) {
		
		// 查询是否存在
		RiskRecordBack temp = riskRecordMapper.getRiskRecordBackByRecordId(riskRecordBack.getRecordId());
		
		if(temp!=null) {
			riskRecordMapper.deleteRiskRecordBackByRecordId(riskRecordBack.getRecordId());
		}
		
		riskRecordMapper.addRiskRecordBack(riskRecordBack);
		riskRecordMapper.updateRiskRecordStatusById(RiskRecordStatus.dealed.toString(), riskRecordBack.getRecordId());
		
		return new SKResult(true, "处理成功");
	}
	
	/**
	 * 获取风险信息详情
	 */
	public Map<String, Object> findRiskInfoById(Integer id){
		
		Map<String, Object> riskRecordMap = riskRecordMapper.findRiskInfoById(id);
		RiskRecordBack riskRecordBack = riskRecordMapper.getRiskRecordBackByRecordId(id);
		if(riskRecordBack==null) {
			riskRecordMap.put("BUSINESS_LICENSE_NO", null);
			riskRecordMap.put("MOBILE_IP", null);
			riskRecordMap.put("ORDER_IP", null);
			riskRecordMap.put("EMAIL_ADDRESS", null);
			riskRecordMap.put("PROCESS_CODE", null);
			return riskRecordMap;
		}else {
			// 如果处理信息不为空，则返回之前填写的处理信息
			riskRecordMap.put("BANK_CARD_NO", riskRecordBack.getBankCardNo());
			riskRecordMap.put("CERT_NO", riskRecordBack.getCertNo());
			riskRecordMap.put("BUSINESS_LICENSE_NO", riskRecordBack.getBusinessLicenseNo());
			riskRecordMap.put("MOBILE", riskRecordBack.getMobile());
			riskRecordMap.put("MOBILE_IP", riskRecordBack.getMobileIp());
			riskRecordMap.put("ORDER_IP", riskRecordBack.getOrderIp());
			riskRecordMap.put("MERCH_NAME", riskRecordBack.getMerchName());
			riskRecordMap.put("EMAIL_ADDRESS", riskRecordBack.getEmailAddress());
			riskRecordMap.put("PROCESS_CODE", riskRecordBack.getProcessCode());
			riskRecordMap.put("MERCHANT_NO", riskRecordBack.getMerchantNo());
			
			return riskRecordMap;
		}
	}
	
	
}
