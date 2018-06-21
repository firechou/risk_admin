package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dao.entity.SysUser;
import com.dao.mapper.SysUserMapper;

@Service
public class SysUserService {
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	/**
	 * 获取当前登录用户信息
	 * @return
	 */
	public SysUser getSessionUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SysUser sysUser = sysUserMapper.getUserByUserName(userDetails.getUsername());
		return sysUser;
	}

}
