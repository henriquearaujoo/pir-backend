package com.samsung.fas.pir.configuration.security.persistence.tasks;

import com.samsung.fas.pir.configuration.security.persistence.repository.IPasswordRecoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetTRIM {
	private IPasswordRecoverRepository repository;

	@Autowired
	public PasswordResetTRIM(IPasswordRecoverRepository repository) {
		this.repository = repository;
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void inactivateTechnicals() {
		repository.trim(1);
	}
}
