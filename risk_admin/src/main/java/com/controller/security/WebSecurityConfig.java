package com.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * 使springboot与springsecurity关联
 * @author zhouy
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomUserService customUserService;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests()
    			.antMatchers("/callback/**").permitAll() // 定义不需要权限就能访问
                .anyRequest().authenticated() // 其他任何请求,登录后才可以访问
                .and()
                
                .formLogin() // 允许用户以基于表单的登录进行身份验证
                .loginPage("/login") // 自定义登录页面
                .loginProcessingUrl("/login") // 登录正在处理页面
                .failureUrl("/login?error=true") // 登录失败页面
                .defaultSuccessUrl("/index") //登录成功跳转url
                .permitAll() //登录页面用户任意访问
                .and()
                
                .logout()
                .logoutUrl("/logout") // 自定义logout路径
                .logoutSuccessUrl("/login") // 退出成功的页面
//                .logoutSuccessHandler(logoutSuccessHandler) 
//                .invalidateHttpSession(true)
                .permitAll()
                .and()
                
                // 记住我
                .rememberMe()
                .tokenValiditySeconds(604800) // cookies有限期是一周，单位秒
                .rememberMeParameter("remember_me") // 登陆时是否激活记住我功能的参数名字
                .rememberMeCookieName("remember_me_cookie"); //cookies的名字，登陆后可以通过浏览器查看cookies名字
                
//                .httpBasic() // 允许用户使用HTTP基本身份验证进行身份验证
                ; 
    	
    	http.csrf().disable(); // 关闭跨站点请求伪造
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	// 设置用户密码的加密md5
        auth.userDetailsService(this.customUserService).passwordEncoder(new Md5PasswordEncoder()); 
        // 设置一个内存用户，该用户无需存在于数据库用户表
        auth.inMemoryAuthentication().withUser("ADMIN").password("ADMIN123").roles("ADMIN");
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/css/**", "/fonts/**", "/js/**");
    }
}
