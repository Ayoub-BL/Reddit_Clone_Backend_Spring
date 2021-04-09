package com.ayoubbl.codingbeans.redditclonebackendspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayoubbl.codingbeans.redditclonebackendspring.dto.SignupDTO;
import com.ayoubbl.codingbeans.redditclonebackendspring.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody SignupDTO signupDTO) {
		authService.signup(signupDTO);
		return new ResponseEntity<>("User Registration successful", HttpStatus.OK);
	}
	
}
