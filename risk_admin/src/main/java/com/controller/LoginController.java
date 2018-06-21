package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value={"/","/index"})
	public String index(){
		return "index";
	}
	
	@RequestMapping(value={"/login"}, method=RequestMethod.GET)
	public String login(){
		return "login";
	}
	
}
