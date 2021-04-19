package com.ayoubbl.codingbeans.redditclonebackendspring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayoubbl.codingbeans.redditclonebackendspring.dto.RequestLoginDto;
import com.ayoubbl.codingbeans.redditclonebackendspring.dto.RequestSignupDto;
import com.ayoubbl.codingbeans.redditclonebackendspring.dto.ResponseLoginDto;
import com.ayoubbl.codingbeans.redditclonebackendspring.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody RequestSignupDto requestSignupDto) {
		return authService.signup(requestSignupDto);
	}
	
	@GetMapping("/accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token) {
		return authService.verifyAccount(token);
	}
	
	@PostMapping("/login")
	public ResponseLoginDto login(@RequestBody RequestLoginDto requestLoginDto) {
		return authService.login(requestLoginDto);
	}
	
}