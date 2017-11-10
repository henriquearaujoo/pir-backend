package com.samsung.fas.pir.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsung.fas.pir.models.dto.user.LUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	public JWTAuthenticationFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	// TODO: Find user
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		try {

			LUser login = new ObjectMapper().readValue(request.getInputStream(), LUser.class);
			// TODO: Get user authorities
			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPassword(), Collections.emptyList()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		String token = 	Jwts.builder().setSubject( ((User) authResult.getPrincipal()).getUsername())
						.signWith(SignatureAlgorithm.HS512, "PiáDoAmazonas".getBytes())
						.compact();
		response.addHeader("Authorization", "PIR " + token);
	}
}