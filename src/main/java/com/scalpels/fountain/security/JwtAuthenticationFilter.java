package com.scalpels.fountain.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtAuthenticationFilter extends GenericFilterBean {

	private  JwtSettings settings;
	
	public JwtAuthenticationFilter(JwtSettings settings) {
		this.settings = settings;
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest)request; 
		String token = httpServletRequest.getHeader(JwtSettings.HEADER);
		Authentication authentication = JwtAuthenticationFactory.getJwtAuthentication(settings,token);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}
}