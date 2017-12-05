package com.samsung.fas.pir.login.providers;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseAuthProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		String		user		= auth.getName();
		String		pass		= auth.getCredentials().toString();
		checkUserCredentials(user, pass);
		User 		userDetails	= getUserDetails(user, pass);
		return new PreAuthenticatedAuthenticationToken(userDetails, pass, userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> type) {
		 return type.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	protected abstract void checkUserCredentials(String username, String password) throws UsernameNotFoundException, BadCredentialsException;
	
	protected abstract User getUserDetails(String username, String password);
}
