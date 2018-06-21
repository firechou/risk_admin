package com.dao.mapper;

import java.util.List;

import com.dao.entity.SysUser;

public interface SysUserMapper {
	
	public SysUser getUserById(int userId);
	
	public SysUser getUserByUserName(String userName);
	
	public List<SysUser> getUserList(String userName);

}
