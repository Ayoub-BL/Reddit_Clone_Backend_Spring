package com.ayoubbl.codingbeans.redditclonebackendspring.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayoubbl.codingbeans.redditclonebackendspring.dto.RequestLoginDto;
import com.ayoubbl.codingbeans.redditclonebackendspring.dto.RequestSignupDto;
import com.ayoubbl.codingbeans.redditclonebackendspring.dto.ResponseLoginDto;
import com.ayoubbl.codingbeans.redditclonebackendspring.model.AppUser;
import com.ayoubbl.codingbeans.redditclonebackendspring.model.NotificationEmail;
import com.ayoubbl.codingbeans.redditclonebackendspring.model.VerificationToken;
import com.ayoubbl.codingbeans.redditclonebackendspring.repository.AppUserRepository;
import com.ayoubbl.codingbeans.redditclonebackendspring.repository.VerificationTokenRepository;
import com.ayoubbl.codingbeans.redditclonebackendspring.security.JwtProvider;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
	
	private final PasswordEncoder passwordEncoder;
	private final AppUserRepository appUserRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final MailService mailService;
	private static final String VERIFY_ACCOUNT_PATH = "http://localhost:8080/api/auth/accountVerification/";
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	
	//@Transactional
	public ResponseEntity<String> signup(RequestSignupDto requestSignupDto) {
		try {
			Optional<AppUser> optionalAppUser = appUserRepository.findByUsername(requestSignupDto.getUsername());
			if (!optionalAppUser.isPresent()) {
				optionalAppUser = appUserRepository.findByEmail(requestSignupDto.getEmail());
				if (!optionalAppUser.isPresent()) {
					AppUser appUser = new AppUser();
					appUser.setUsername(requestSignupDto.getUsername());
					appUser.setEmail(requestSignupDto.getEmail());
					appUser.setPassword(passwordEncoder.encode(requestSignupDto.getPassword()));
					appUser.setEnabled(false);
					appUserRepository.save(appUser);
					String token = generateVerificationToken(appUser);
					if (!token.isEmpty()) {
						mailService.sendMail(new NotificationEmail(appUser.getEmail(), VERIFY_ACCOUNT_PATH + token));
						return new ResponseEntity<>("User registration successful - you can now check your email to active your account.", HttpStatus.OK);
					} else
						return new ResponseEntity<>("Empty token!", HttpStatus.EXPECTATION_FAILED);
				} else
					return new ResponseEntity<>("Email already exists!", HttpStatus.FOUND);
			} else
				return new ResponseEntity<>("Username already exists!", HttpStatus.FOUND);
		} catch (Exception e) {
			String msg = "Exception occurred at " + AuthService.class.getSimpleName() + ".signup() method!";
			log.error(msg, e);
			return new ResponseEntity<>("Exception : signup failed!", HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	private String generateVerificationToken(AppUser appUser) {
		try {
			String token1 = UUID.randomUUID().toString();
			String token2 = UUID.randomUUID().toString();
			String token = token1 + token2;
			VerificationToken verificationToken = new VerificationToken();
			verificationToken.setToken(token);
			verificationToken.setAppUser(appUser);
			verificationTokenRepository.save(verificationToken);
			return token;
		} catch (Exception e) {
			String msg = "Exception occurred at " + AuthService.class.getSimpleName() + ".generateVerificationToken() method!";
			log.error(msg, e);
			return "";
		}
	}
	
	public ResponseEntity<String> verifyAccount(String token) {
		try {
			Optional<VerificationToken> optionalVerificationToken = verificationTokenRepository.findByToken(token);
			if (optionalVerificationToken.isPresent())
				return fetchAndEnableAppUser(optionalVerificationToken.get());
			else
				return new ResponseEntity<>("Invalid Token!", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			String msg = "Exception occurred at " + AuthService.class.getSimpleName() + ".verifyAccount() method!";
			log.error(msg, e);
			return new ResponseEntity<>("Exception : verify account failed!", HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@Transactional
	private ResponseEntity<String> fetchAndEnableAppUser(VerificationToken verificationToken) {
		try {
			Long appUserId = verificationToken.getAppUser().getId();
			Optional<AppUser> optionalAppUser = appUserRepository.findById(verificationToken.getAppUser().getId());
			if (optionalAppUser.isPresent()) {
				AppUser appUser = optionalAppUser.get();
				if (!appUser.isEnabled()) {
					appUser.setEnabled(true);
					appUserRepository.save(optionalAppUser.get());
					return new ResponseEntity<>("User account has been activated successfully.", HttpStatus.OK);
				} else
					return new ResponseEntity<>("User account already active!", HttpStatus.ALREADY_REPORTED);
			} else
				return new ResponseEntity<>("No user found with id: \"" + appUserId + "\"!", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			String msg = "Exception occurred at " + AuthService.class.getSimpleName() + ".fetchAndEnableAppUser() method!";
			log.error(msg, e);
			return new ResponseEntity<>("Exception : enable user failed!", HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	public ResponseLoginDto login(RequestLoginDto requestLoginDto) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestLoginDto.getUsername(), 
					requestLoginDto.getPassword()));
			// Store the authenticated user into Spring Security Context :
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String authenticationToken = jwtProvider.generateToken(authentication);
			return new ResponseLoginDto(requestLoginDto.getUsername(), authenticationToken);
		} catch (AuthenticationException e) {
			String msg = "Exception occurred at " + AuthService.class.getSimpleName() + ".login() method!";
			log.error(msg, e);
			return new ResponseLoginDto();
		}
	}
	
}