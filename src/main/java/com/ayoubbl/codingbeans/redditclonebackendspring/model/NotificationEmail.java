package com.ayoubbl.codingbeans.redditclonebackendspring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("unused")
public class NotificationEmail {
	private String recipient;
	private String subject;
	private String body;
}