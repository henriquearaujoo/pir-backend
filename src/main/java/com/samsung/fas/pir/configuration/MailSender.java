package com.samsung.fas.pir.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Configuration
public class MailSender {
	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl	sender 		= new JavaMailSenderImpl();
		Properties			properties	= new Properties();

		sender.setDefaultEncoding(StandardCharsets.UTF_8.name());
		sender.setProtocol("smtp");
		sender.setHost("smtp.gmail.com");
		sender.setPort(587);
		sender.setUsername("pir@fas-amazonas.org");
		sender.setPassword("FAS123pir");
		properties.put("mail.smtps.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.debug", "true");
		sender.setJavaMailProperties(properties);
		return sender;
	}
}
