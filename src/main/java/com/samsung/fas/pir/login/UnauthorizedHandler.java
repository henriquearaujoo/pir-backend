package com.samsung.fas.pir.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class UnauthorizedHandler extends BasicAuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName()); 
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
		PrintWriter writer = response.getWriter(); 
		writer.print("http.status.401.unauthorized"); 
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		setRealmName("PIR"); 
		super.afterPropertiesSet(); 
	}
}
