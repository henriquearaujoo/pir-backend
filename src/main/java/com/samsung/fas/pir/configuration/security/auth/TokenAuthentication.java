package com.samsung.fas.pir.configuration.security.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenAuthentication extends AbstractAuthenticationToken {
	private final 	String		token;
	private	final	UserDetails	details;

	TokenAuthentication(UserDetails details, String token) {
		super(details.getAuthorities());
		this.details 	= details;
		this.token		= token;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	@Override
	public UserDetails getPrincipal() {
		return details;
	}
}