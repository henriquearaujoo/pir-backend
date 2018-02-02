package com.samsung.fas.pir.login.auth;

import com.google.common.hash.Hashing;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.login.rest.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class AuthManager implements AuthenticationManager {
	private 	AccountService 		service;
	private 	PasswordEncoder		encoder;

	@Autowired
	public AuthManager(AccountService service, PasswordEncoder encoder) {
		this.service 	= service;
		this.encoder	= encoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String			username		= (String) authentication.getPrincipal();
		String			password		= Hashing.sha256().hashString((String) authentication.getCredentials(), StandardCharsets.UTF_8).toString();
		Account			account			= (Account) service.loadUserByUsername(username);

		if (account == null)
			throw new UsernameNotFoundException("user.notfound");

		if (!account.isEnabled())
			throw new DisabledException("user.disabled");

		if (!encoder.matches(password, account.getPassword()))
			throw new BadCredentialsException("user.password.mismatch");

		return new UsernamePasswordAuthenticationToken(account, password, account.getAuthorities());
	}
}
