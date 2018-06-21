package com.controller.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dao.entity.SysAuth;
import com.dao.entity.SysUser;
import com.dao.mapper.SysAuthMapper;
import com.dao.mapper.SysUserMapper;

/**
 * 获取用户数据库配置的权限
 * @author zhouy
 *
 */
@Service
public class CustomUserService implements UserDetailsService{

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysAuthMapper sysAuthMapper;
	
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		
		SysUser user = sysUserMapper.getUserByUserName(userName);
        if (user != null) {
        	// 获取用户权限
            List<SysAuth> sysAuths = sysAuthMapper.findAuthByUserId(user.getId());
            // 添加用户权限到spring security
            List<GrantedAuthority> grantedAuthorities = new ArrayList <>();
            for (SysAuth sysAuth : sysAuths) {
                if (sysAuth != null && sysAuth.getCode()!=null) {
	                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysAuth.getCode());
	                grantedAuthorities.add(grantedAuthority);
                }
            }
            return new User(user.getUserName(), user.getPassword(), true, true, true, true, grantedAuthorities);
        } else {
        	// 用户不存在
            throw new UsernameNotFoundException("用户" + userName + "不存在");
        }
        
	}
	
}
