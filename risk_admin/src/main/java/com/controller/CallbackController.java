package com.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.dao.entity.RiskRecord;
import com.dao.mapper.RiskRecordMapper;
import com.utils.RiskRecordStatus;

/**
 * 上游回调
 * @author zhouy
 *
 */
@Controller
@RequestMapping("/callback")
public class CallbackController {
	
	private static final Logger log = LoggerFactory.getLogger(CallbackController.class);
	
	@Value("${PUBLIC_KEY}")
	private String publicKey; // 支付宝公钥
	
	@Autowired
	private RiskRecordMapper riskRecordMapper;
	
	/**
	 * 接收支付宝风险推送
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value="/alipayRiskgo")
	public String alipayRiskgo(@RequestParam Map<String, String> params, HttpServletRequest request) throws IOException {
		log.info("请求参数：{}", params);
		
		// 验签
		try {
			boolean isChecked = AlipaySignature.rsaCheckV1(params, this.publicKey, "GBK", "RSA2");
			if(!isChecked) {
				log.info("验签失败");
				return "验签失败";
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
			log.error("验签异常");
			return "验签异常";
		}
		
		String pid = params.get("pid"); // 商户pid
		String[] tradeNos = params.get("tradeNos").split(","); // 涉嫌商户被投诉的外部交易号样例
		String riskType = params.get("risktype"); // 风险类型
		String riskLevel = params.get("risklevel"); // 风险情况描述
		
		for(int i=0; i<tradeNos.length; i++) {
			String tradeNo = tradeNos[i].trim();
			// 过滤已通知的订单号
			// 一个订单号可能通知多次，不要幂等
//			RiskRecord riskRecord = riskRecordMapper.getRiskRecordByTradeNo(tradeNo);
//			if(riskRecord!=null) {
//				log.info("该风险订单已通知，订单号：{}", tradeNo);
//				return "success";
//			}
			// 入库
			RiskRecord newRiskRecord = new RiskRecord();
			newRiskRecord.setPid(pid);
			newRiskRecord.setTradeNo(tradeNo);
			newRiskRecord.setRiskType(riskType);
			newRiskRecord.setRiskLevel(riskLevel);
			newRiskRecord.setRiskStatus(RiskRecordStatus.wait_deal.toString()); // 待处理
			int num = riskRecordMapper.addRiskRecord(newRiskRecord);
			if(num<=0) {
				log.error("入库失败，待入库信息：{}", newRiskRecord);
				return "入库失败";
			}
		}
		
		return "success";
	}
	
}
