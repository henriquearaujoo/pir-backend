package com.samsung.fas.pir.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class CSRFConfiguration {
	@Bean
	public CookieCsrfTokenRepository cookieCsrfTokenRepository() {
		return CookieCsrfTokenRepository.withHttpOnlyFalse();
	}
}