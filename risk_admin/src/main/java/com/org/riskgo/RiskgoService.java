package com.org.riskgo;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySecurityRiskCustomerriskSendRequest;
import com.alipay.api.response.AlipaySecurityRiskCustomerriskSendResponse;
import com.dao.entity.RiskRecordBack;
import com.utils.SKResult;

@Service
public class RiskgoService {
	
	private static final Logger log = LoggerFactory.getLogger(RiskgoService.class);

	@Value("${SERVER_URL}")
	private String serverUrl;
	
	@Value("${APP_ID}")
	private String appId;
	
	@Value("${APP_PRIVATE_KEY}")
	private String appPrivateKey;
	
	@Value("${ALIPAY_PUBLIC_KEY}")
	private String alipayPublicKey;

	public SKResult send(RiskRecordBack riskRecordBack) {

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("plat_account", riskRecordBack.getMerchantNo()); // 平台商户号merchant_no
		requestMap.put("trade_no", riskRecordBack.getTradeNo()); // 平台流水号trans_no
		requestMap.put("pid", riskRecordBack.getPid());
		requestMap.put("bank_card_no", riskRecordBack.getBankCardNo()); // 可空
		requestMap.put("cert_no", riskRecordBack.getCertNo()); // 可空
		requestMap.put("business_license_no", riskRecordBack.getBusinessLicenseNo()); // 可空
		requestMap.put("mobile", riskRecordBack.getMobile()); // 可空
		requestMap.put("mobile_ip", riskRecordBack.getMobileIp()); // 可空
		requestMap.put("order_ip", riskRecordBack.getOrderIp()); // 可空
		requestMap.put("merch_name", riskRecordBack.getMerchName()); // 可空
		requestMap.put("email_address", riskRecordBack.getEmailAddress()); // 可空
		requestMap.put("process_code", riskRecordBack.getProcessCode());
		
		String bizContent = JSON.toJSONString(requestMap, SerializerFeature.WriteMapNullValue); // 过滤输出值为null的字段
		
		log.info("请求参数："+bizContent);
		
		AlipaySecurityRiskCustomerriskSendRequest request = new AlipaySecurityRiskCustomerriskSendRequest();

		request.setBizContent(bizContent);
		
		AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, appPrivateKey, "json", "GBK",
				alipayPublicKey, "RSA2");
		
		try {
			AlipaySecurityRiskCustomerriskSendResponse response = alipayClient.execute(request);
			
			log.info("返回参数："+response.getBody());
			
			if(!response.isSuccess()) {
				// 请求失败
				return new SKResult(false, "请求支付宝失败");
			}
			if(!"10000".equals(response.getCode())) {
				// 调用接口失败
				return new SKResult(false, response.getMsg());
			}
			
		} catch (AlipayApiException e) {
			e.printStackTrace();
			return new SKResult(false, "请求支付宝异常");
		}
		
		return new SKResult(true, "处理成功");
	}

}
