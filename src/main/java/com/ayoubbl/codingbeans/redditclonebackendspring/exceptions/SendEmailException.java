package com.ayoubbl.codingbeans.redditclonebackendspring.exceptions;

public class SendEmailException extends RuntimeException {
	
	private static final long serialVersionUID = 4724519963609625729L;
	
	public SendEmailException(String exMessage) {
		super(exMessage);
	}
	
	public SendEmailException(String exMessage, Exception exception) {
		super(exMessage, exception);
	}
	
}