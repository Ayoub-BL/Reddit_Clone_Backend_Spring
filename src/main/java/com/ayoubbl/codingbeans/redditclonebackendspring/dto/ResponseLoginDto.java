package com.ayoubbl.codingbeans.redditclonebackendspring.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLoginDto {
	
	@NotBlank(message = "Username is required")
	private String username;
	
	@NotBlank(message = "AuthenticationToken Token is required")
	private String authenticationToken;
	
}
