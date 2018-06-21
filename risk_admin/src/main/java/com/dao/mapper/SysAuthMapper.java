package com.dao.mapper;

import java.util.List;

import com.dao.entity.SysAuth;
public interface SysAuthMapper {
	
	public List<SysAuth> findAuthByUserId(int userId);
	
	
}
