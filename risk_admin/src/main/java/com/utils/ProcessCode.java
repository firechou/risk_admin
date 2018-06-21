package com.utils;

/**
 * 对应上游提供的处理码和描述
 * 参考：
 * https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.Pn2692&treeId=482&articleId=107733&docType=1
 * @author zhouy
 *
 */

public enum ProcessCode {
	
	code_01("01","暂停发货"),
	code_02("02","延迟结算"),
	code_03("03","关停账户"),
	code_04("04","暂停发货+关停账户"),
	code_05("05","延迟结算+关停账户"),
	code_06("06","其他"),
	code_07("07","平台进行退款退订"),
	code_08("08","平台跟用户沟通后，用户撤诉"),
	;
	
	private String code;
	private String desc;
	
	private ProcessCode(String code, String desc) {
		this.code = code;
		this.desc = desc; 
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
