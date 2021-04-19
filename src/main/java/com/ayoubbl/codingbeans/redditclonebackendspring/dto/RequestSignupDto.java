package com.ayoubbl.codingbeans.redditclonebackendspring.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestSignupDto {
	
	@NotBlank(message = "Username is required")
	private String username;
	
	@NotBlank(message = "Email is required")
	@Email
	private String email;
	
	@NotBlank(message = "Password is required")
	private String password;
	
}
