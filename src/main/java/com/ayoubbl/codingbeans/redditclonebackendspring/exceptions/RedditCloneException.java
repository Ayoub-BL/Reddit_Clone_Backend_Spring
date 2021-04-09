package com.ayoubbl.codingbeans.redditclonebackendspring.exceptions;

public class RedditCloneException extends RuntimeException {
	
	private static final long serialVersionUID = 4724519963609625729L;
	
	public RedditCloneException(String exMessage) {
		super(exMessage);
	}
	
	public RedditCloneException(String exMessage, Exception exception) {
		super(exMessage, exception);
	}
	
}