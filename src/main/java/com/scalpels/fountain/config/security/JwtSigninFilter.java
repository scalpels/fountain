package com.scalpels.fountain.config.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtSigninFilter extends AbstractAuthenticationProcessingFilter {

	private  JwtSettings settings ;
	
	public JwtSigninFilter(String url, JwtSettings settings ,AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		this.settings = settings;
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException, IOException, ServletException {
		AccountCredentials credentials = new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
				credentials.getUsername(), credentials.getPassword(), Collections.emptyList()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		String jwtHeader = JwtAuthenticationFactory.generateJwtAuthentication(settings, auth.getName(), auth.getAuthorities());
		res.addHeader(JwtSettings.HEADER, jwtHeader);
	}
}