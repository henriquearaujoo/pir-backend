package com.samsung.fas.pir.configuration.security.auth;

import com.samsung.fas.pir.configuration.security.rest.service.AccountService;
import com.samsung.fas.pir.exception.RESTException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
	private 	JWToken 		token;
	private 	AccountService service;

	public TokenAuthenticationFilter(JWToken token, AccountService service) {
		this.token 		= token;
		this.service 	= service;
	}

	@Override
	public void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain) {
		try {
			String username;
			String authToken = token.getToken(request);

			if (authToken != null) {
				username = token.getUserLogin(authToken);
				if (username != null) {
					// TODO: Get UserDetails from token
					UserDetails userDetails = service.loadUserByUsername(username);
					if (token.validateToken(authToken, userDetails)) {
						TokenAuthentication authentication = new TokenAuthentication(userDetails, authToken);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			}
			chain.doFilter(request, response);
		} catch (IOException | ServletException | BadCredentialsException e) {
			throw new RESTException(e.getMessage());
		}
	}
}