package com.ayoubbl.codingbeans.redditclonebackendspring.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ayoubbl.codingbeans.redditclonebackendspring.model.NotificationEmail;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
class MailContentBuilder {
	
	private final TemplateEngine templateEngine;
	
	String build(NotificationEmail notificationEmail) {
		Context context = new Context();
		context.setVariable("emailSubject", notificationEmail.getSubject());
		context.setVariable("appLogoUrl", notificationEmail.getAppLogoUrl());
		context.setVariable("emailRecipient", notificationEmail.getRecipient());
		context.setVariable("emailBody", notificationEmail.getBody());
		context.setVariable("emailSupportTeam", notificationEmail.getEmailSupportTeam());
		return templateEngine.process("mailTemplate", context);
	}
	
}