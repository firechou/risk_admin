package com.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dao.entity.RiskRecord;
import com.dao.entity.RiskRecordBack;
import com.dao.mapper.RiskRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.org.riskgo.RiskgoService;
import com.service.RiskService;
import com.service.SysUserService;
import com.utils.ProcessCode;
import com.utils.RiskRecordStatus;
import com.utils.SKResult;

@Controller
@RequestMapping("/risk")
public class RiskController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(RiskController.class);
	
	@Autowired
	private RiskRecordMapper riskRecordMapper;
	@Autowired
	private RiskgoService riskgoService;
	@Autowired
	private RiskService riskService;
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping({"/",""})
	public String risk(Model model){
		// 风险状态select
		Map<String, Object> riskStatus = new LinkedHashMap<>();
		for(RiskRecordStatus e : RiskRecordStatus.values()) {
			riskStatus.put(e.toString(), e.getDesc());
		}
		model.addAttribute("riskStatus",riskStatus);
		return "risk/risk_query";
	}

	@RequestMapping(value="/input", method=RequestMethod.GET)
	public String input(@RequestParam("id") Integer id, Model model){
		
		// 获取风险信息
		Map<String, Object> riskRecord = riskService.findRiskInfoById(id);
		model.addAttribute("riskRecord",riskRecord);
		
		// 风险状态select
		Map<String, Object> riskStatus = new LinkedHashMap<>();
		for(RiskRecordStatus e : RiskRecordStatus.values()) {
			riskStatus.put(e.toString(), e.getDesc());
		}
		model.addAttribute("riskStatus",riskStatus);
		
		// 处理结果select
		Map<String, Object> processCode = new LinkedHashMap<>();
		for(ProcessCode e : ProcessCode.values()) {
			processCode.put(e.getCode(), e.getDesc());
		}
		model.addAttribute("processCode",processCode);
		
		return "risk/risk_input";
	}
	
	/**
	 * 删除记录
	 */
	@RequestMapping(value="/del", method=RequestMethod.GET)
	@ResponseBody
	public Object del(@RequestParam("id") Integer id){
		
		log.info("请求参数：{}", id);
	
		return riskService.deleteRisk(id);
	}
	/**
	 * 关闭记录状态
	 */
	@RequestMapping(value="/toggle", method=RequestMethod.GET)
	@ResponseBody
	public Object toggle(@RequestParam("id") Integer id){
		
		log.info("请求参数：{}", id);
		
		return riskService.offRiskStatus(id);
	}
	
	/**
	 * 处理风险记录
	 */
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	@ResponseBody
	public Object edit(@RequestParam Map<String, String> params){
		
		log.info("请求参数：{}", params);
		RiskRecord riskRecord = riskRecordMapper.getRiskRecordById(Integer.parseInt(params.get("id")));
		
		if(riskRecord==null) {
			return new SKResult(false, "风险记录不存在");
		}
		// 一个风险订单可多次处理，即多次回传给支付宝，但本地只保存最后一条成功回传记录
//		if(RiskRecordStatus.dealed.toString().equals(riskRecord.getRiskStatus())) {
//			return new SKResult(false, "风险记录"+RiskRecordStatus.dealed.getDesc());
//		}
		// 关闭的风险订单，相当于对数据库的逻辑删除，关闭后不能再处理
		if(RiskRecordStatus.off.toString().equals(riskRecord.getRiskStatus())) {
			return new SKResult(false, "风险记录"+RiskRecordStatus.off.getDesc());
		}
		
		RiskRecordBack riskRecordBack = new RiskRecordBack();
		riskRecordBack.setRecordId(riskRecord.getId());
		riskRecordBack.setPid(riskRecord.getPid());
		riskRecordBack.setMerchantNo(params.get("merchant_no"));
		riskRecordBack.setTradeNo(riskRecord.getTradeNo());
		riskRecordBack.setBankCardNo(params.get("bank_card_no"));
		riskRecordBack.setCertNo(params.get("cert_no"));
		riskRecordBack.setBusinessLicenseNo(params.get("business_license_no"));
		riskRecordBack.setMobile(params.get("mobile"));
		riskRecordBack.setMobileIp(params.get("mobile_ip"));
		riskRecordBack.setOrderIp(params.get("order_ip"));
		riskRecordBack.setMerchName(params.get("merch_name"));
		riskRecordBack.setEmailAddress(params.get("email_address"));
		riskRecordBack.setProcessCode(params.get("process_code"));
		riskRecordBack.setOperateUser(sysUserService.getSessionUser().getId()); // 当前登录用户id
		
		
		// 向支付宝回传
		SKResult sk = riskgoService.send(riskRecordBack);
		if(!sk.isResult()) {
			return sk;
		}
		
		// 入库
		sk = riskService.addRiskRecordBack(riskRecordBack);
		
		return sk;
		
	}
	
	/**
	 * 查询列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	public String listData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> params) throws Exception{
		
		log.info("请求参数：{}", params);
		
		int pageNumber = Integer.parseInt(params.get("pageNumber"));
		int pageSize = Integer.parseInt(params.get("pageSize"));
		
		PageHelper.startPage(pageNumber, pageSize);
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("trade_no", params.get("trade_no"));
		queryParams.put("risk_status", params.get("risk_status"));
		queryParams.put("merchant_no", params.get("merchant_no"));
		List<Map<String, Object>> riskList = riskRecordMapper.findRiskInfo(queryParams);
		PageInfo<Map<String, Object>> page = new PageInfo<>(riskList);
		
		// 结果封装
		Map<String, Object> respMap = new HashMap<String, Object>();
		respMap.put("total", page.getTotal());
		respMap.put("rows", page.getList());
		String jsonStr = JSON.toJSONString(respMap);
		log.info("返回报报文：{}", jsonStr);
		return jsonStr;
	}
	
}
