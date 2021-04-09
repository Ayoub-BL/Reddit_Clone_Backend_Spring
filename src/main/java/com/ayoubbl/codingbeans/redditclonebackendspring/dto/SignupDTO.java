package com.ayoubbl.codingbeans.redditclonebackendspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDTO {
	
	private String username;
	private String email;
	private String password;
	
}
