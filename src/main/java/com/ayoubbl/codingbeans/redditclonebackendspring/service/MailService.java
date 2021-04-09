package com.ayoubbl.codingbeans.redditclonebackendspring.service;

import javax.mail.Message.RecipientType;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.ayoubbl.codingbeans.redditclonebackendspring.exceptions.SendEmailException;
import com.ayoubbl.codingbeans.redditclonebackendspring.model.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
class MailService {
	
	private final JavaMailSender mailSender;
	private final MailContentBuilder mailContentBuilder;
	
	void sendMail(NotificationEmail notificationEmail) {
		MimeMessagePreparator mimeMessagePreparator = (MimeMessage mimeMessage) -> {
			mimeMessage.setFrom(notificationEmail.getNoReplaySender());
			mimeMessage.addRecipients(RecipientType.TO, notificationEmail.getRecipient());
			mimeMessage.setSubject(notificationEmail.getSubject());
			mimeMessage.setText(mailContentBuilder.build(notificationEmail));
			/*
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
			mimeMessageHelper.setFrom(notificationEmail.getNoReplaySender());
			mimeMessageHelper.setTo(notificationEmail.getRecipient());
			mimeMessageHelper.setSubject(notificationEmail.getSubject());
			mimeMessageHelper.setText(mailContentBuilder.build(notificationEmail));
			*/
		};
		try {
			mailSender.send(mimeMessagePreparator);
			log.info("Activation email sent!!");
		} catch (MailException e) {
			String exMessage = "Exception occurred when sending email to " + notificationEmail.getRecipient();
			log.error(exMessage, e);
			throw new SendEmailException(exMessage, e);
		}
	}
	
}