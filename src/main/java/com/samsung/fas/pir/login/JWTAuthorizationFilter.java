package com.samsung.fas.pir.login;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String header = request.getHeader("Authorization");

		if (header != null && !header.startsWith("PIR")) {
			chain.doFilter(request, response);
		}
		SecurityContextHolder.getContext().setAuthentication(getAuthentication(request));
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token != null) {
			// parse the token.
			String user = Jwts.parser().setSigningKey("PiáDoAmazonas".getBytes())
						  .parseClaimsJws(token.replace("PIR ", ""))
						  .getBody()
						  .getSubject();

			if (user != null) {
				// TODO: Get user authorities
				return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
			}
			return null;
		}
		return null;
	}
}
