package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(BaseController.class);
	
//	private static final String CHARSET = "utf-8";
	
	/**
	 * 输出json格式字符串
	 * @param obj
	 * @param response
	 */
	protected void outJson(Object obj, HttpServletResponse response){
		String content = JSON.toJSONString(obj);
		log.info("响应参数：{}", content);
		PrintWriter pw;
		try {
			response.setContentType("application/json; charset=utf-8");
			pw = response.getWriter();
			pw.write(content);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
