package com.utils;
/**
 * 通用结果实体
 * @author zhouy
 *
 */
public class SKResult {
	
	private boolean isResult;
	private String msg;
	private Object obj;
	
	public SKResult(boolean isResult, String msg, Object obj) {
		super();
		this.isResult = isResult;
		this.msg = msg;
		this.obj = obj;
	}
	public SKResult(boolean isResult, String msg) {
		super();
		this.isResult = isResult;
		this.msg = msg;
	}
	public boolean isResult() {
		return isResult;
	}
	public void setResult(boolean isResult) {
		this.isResult = isResult;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	@Override
	public String toString() {
		return "SKResult [isResult=" + isResult + ", msg=" + msg + ", obj=" + obj + "]";
	}
	
}
