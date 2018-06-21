package com.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * 针对特定的请求指定编码
 * 
 * @author zhouy
 *
 */
@Configuration
@ConfigurationProperties(prefix="spring.http.encoding")
public class MutiCharacterEncodingFilter extends CharacterEncodingFilter implements Ordered {
	
	// 最高优先级
	private int order = Integer.MIN_VALUE;
	
	private List<String> mutiUrls = new ArrayList<>();
	
	private String mutiCharset;
	
    public List<String> getMutiUrls() {
		return mutiUrls;
	}

	public void setMutiUrls(List<String> mutiUrls) {
		this.mutiUrls = mutiUrls;
	}

	public String getMutiCharset() {
		return mutiCharset;
	}

	public void setMutiCharset(String mutiCharset) {
		this.mutiCharset = mutiCharset;
	}

	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		if(StringUtils.isNotBlank(mutiCharset) && mutiUrls!=null) {
			
			String path = request.getServletPath();
	    	
	    	AntPathMatcher matcher = new AntPathMatcher();
	    	
	    	for(int i=0;i<mutiUrls.size();i++) {
	    		if(matcher.match(mutiUrls.get(i), path)) {
	    			request.setCharacterEncoding(mutiCharset);
	            	filterChain.doFilter(request, response);
	            	return;
	    		}
	    	}
			
		}
			
		super.doFilterInternal(request,response,filterChain);
		
    }

	/**
	 * Spring提供了Ordered这个接口，来处理相同接口实现类的优先级问题
	 */
	@Override
	public int getOrder() {
		return order;
	}

}
