package com.samsung.fas.pir.login.rest.service;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmailService {
	@Autowired	private		JavaMailSender		emailSender;
	@Autowired	private 	Configuration 		configuration;

	@Async
	public void sendSimpleMessage(String to, String from, String subject, Map<String, Object> content) throws MessagingException, IOException, TemplateException {
		MimeMessage			message		= emailSender.createMimeMessage();
		MimeMessageHelper	helper		= new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

		configuration.setClassForTemplateLoading(this.getClass(), "/templates/");
		helper.setTo(to);
		helper.setText(FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("email-template.html"), content), true);
		helper.setSubject(subject);
		helper.setFrom(from);

		emailSender.send(message);
	}
}
