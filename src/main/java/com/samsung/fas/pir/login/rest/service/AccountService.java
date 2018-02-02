package com.samsung.fas.pir.login.rest.service;

import com.google.common.hash.Hashing;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.login.persistence.models.entity.PasswordRecover;
import com.samsung.fas.pir.login.persistence.repository.IAccountRepository;
import com.samsung.fas.pir.login.persistence.repository.IPasswordRecoverRepository;
import com.samsung.fas.pir.login.rest.dto.ResetPasswordDTO;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.RandomStringUtils;
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

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AccountService implements UserDetailsService {
	private	static	final	Log						LOG						= LogFactory.getLog(AccountService.class);

	@Autowired			private		IAccountRepository 			repository;
	@Autowired 			private 	IPasswordRecoverRepository	prepository;
	@Autowired			private 	EmailService				emailservice;
	@Autowired	@Lazy	private 	PasswordEncoder 			encoder;
	@Autowired	@Lazy	private 	AuthenticationManager 		manager;

	@Override
	public UserDetails loadUserByUsername(String username) {
		Account account = repository.findByUsername(username);
		if (account == null)
			throw new UsernameNotFoundException("login.username.notfound");
		return account;
	}

	public String recoverPasswordByUserEmail(String email) {
		Account				account		= repository.findByUserEmail(email);
		PasswordRecover		precover	= prepository.findByEmail(email);
		Map<String, Object>	content		= new HashMap<>();
		String 				token;

		if (account == null)
			throw new UsernameNotFoundException("login.email.notfound");

		// Genarate token
		token		= UUID.randomUUID().toString() + RandomStringUtils.random(20, true, true);
		token		= encoder.encode(token).replaceAll("[.$/]", "").substring(4);
		precover	= precover == null? new PasswordRecover() : precover;

		precover.setEmail(email);
		precover.setToken(token);
		precover.setUntil(Date.from(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(1).atZone(ZoneId.systemDefault()).toInstant()));

		content.put("name", account.getUser().getName());
		content.put("link", "http://localhost:4200/reset-password?reset=" + token);
		content.put("signature", "Fundação Amazônia Sustentável");
		content.put("location", "Amazonas, Brazil");

		if (prepository.save(precover) != null) {
			try {
				emailservice.sendSimpleMessage(email, "noreply@itn.com.br", "Recuperar Senha", content);
				return "email.sent";
			} catch (MessagingException | IOException | TemplateException e) {
				e.printStackTrace();
			}
		}
		throw new RESTRuntimeException("password.recovery.error");
	}

	public String resetPassword(ResetPasswordDTO dto) {
		PasswordRecover		recover		= prepository.findByToken(dto.getToken());

		if (recover == null)
			throw new UsernameNotFoundException("invalid.token");

		Account				account		= repository.findByUserEmail(recover.getEmail());

		// Redundant
		if (account == null)
			throw new UsernameNotFoundException("user.notfound");

		account.setPassword(encoder.encode(Hashing.sha256().hashString(dto.getPassword(), StandardCharsets.UTF_8).toString()));
		prepository.delete(recover.getId());

		if (repository.save(account) != null)
			return "password.reseted";

		throw new RESTRuntimeException("password.reset.error");
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
