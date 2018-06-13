package com.samsung.fas.pir.configuration.security.rest.service;

import com.google.common.hash.Hashing;
import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.configuration.security.persistence.models.PasswordRecover;
import com.samsung.fas.pir.configuration.security.persistence.repository.IAccountRepository;
import com.samsung.fas.pir.configuration.security.persistence.repository.IPasswordRecoverRepository;
import com.samsung.fas.pir.configuration.security.rest.dto.ResetPasswordDTO;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
	private	final 	IAccountRepository 			repository;
	private	final 	IPasswordRecoverRepository 	prepository;
	private	final	EmailService				emailservice;
	private final 	PasswordEncoder 			encoder;

	@Autowired
	public AccountService(IAccountRepository repository, IPasswordRecoverRepository prepository, EmailService emailservice, PasswordEncoder encoder) {
		this.repository		= repository;
		this.prepository	= prepository;
		this.emailservice	= emailservice;
		this.encoder		= encoder;
	}

//	@Cacheable("accountsCache")
	@Override
	public UserDetails loadUserByUsername(String username) {
		Account account = repository.findByUsername(username);
		if (account == null)
			throw new UsernameNotFoundException("login.username.notfound");
		return account;
	}

	public String recoverPasswordByUserEmail(String email) {
		Account				account		= repository.findByUserEmail(email);
		PasswordRecover 	precover	= prepository.findByEmail(email);
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
		content.put("link", "http://localhost:4200/#/login/reset-password?reset=" + token);
		content.put("signature", "Fundação Amazônia Sustentável");
		content.put("location", "Amazonas, Brazil");

		try {
			prepository.save(precover);
			emailservice.sendSimpleMessage(email, "noreply@timeitn.com.br", "Recuperar Senha", content);
			return "email.sent";
		} catch (MessagingException | IOException | TemplateException e) {
			e.printStackTrace();
			return "email.failed";
		}
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
		prepository.deleteById(recover.getId());

		return "password.reseted";
	}
}
