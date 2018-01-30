package com.samsung.fas.pir.login.rest.service;

import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.login.persistence.repository.IAccountRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {
	private	static	final	Log						LOG						= LogFactory.getLog(AccountService.class);
	private 				IAccountRepository 		repository;
	private 				PasswordEncoder 		encoder;
	private 				AuthenticationManager 	manager;

	@Autowired
	public AccountService(IAccountRepository repository, @Lazy PasswordEncoder encoder, @Lazy AuthenticationManager manager) {
		this.repository		= repository;
		this.manager		= manager;
		this.encoder		= encoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		Account account = repository.findByUsername(username);
		if (account == null)
			throw new UsernameNotFoundException("login.username.notfound");
		return account;
	}

	public void changePassword(String oldPassword, String newPassword) {
		Authentication	currentUser = SecurityContextHolder.getContext().getAuthentication();
		String			username	= currentUser.getName();

		if (manager != null) {
			LOG.debug("Re-authenticating user '"+ username + "' for password change request.");
			manager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
		} else {
			LOG.debug("No authentication manager set. can't change Password!");
			return;
		}
		LOG.debug("Changing password for user '"+ username + "'");

		Account 		account 	= (Account) loadUserByUsername(username);
		account.setPassword(encoder.encode(newPassword));
		repository.save(account);
	}
}
