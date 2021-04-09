package com.ayoubbl.codingbeans.redditclonebackendspring.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayoubbl.codingbeans.redditclonebackendspring.dto.SignupDTO;
import com.ayoubbl.codingbeans.redditclonebackendspring.exceptions.RedditCloneException;
import com.ayoubbl.codingbeans.redditclonebackendspring.model.AppUser;
import com.ayoubbl.codingbeans.redditclonebackendspring.model.NotificationEmail;
import com.ayoubbl.codingbeans.redditclonebackendspring.model.VerificationToken;
import com.ayoubbl.codingbeans.redditclonebackendspring.repository.AppUserRepository;
import com.ayoubbl.codingbeans.redditclonebackendspring.repository.VerificationTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	
	private final PasswordEncoder passwordEncoder;
	private final AppUserRepository appUserRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final MailService mailServise;
	private static final String VERIFY_ACCOUNT_PATH = "http://localhost:8080/api/auth/accountVerification/";
	
	@Transactional
	public void signup(SignupDTO signupDTO) {
		AppUser appUser = new AppUser();
		appUser.setUsername(signupDTO.getUsername());
		appUser.setEmail(signupDTO.getEmail());
		appUser.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
		appUser.setEnabled(false);
		appUserRepository.save(appUser);
		String token = generateVerificationToken(appUser);
		mailServise.sendMail(new NotificationEmail(appUser.getEmail(), VERIFY_ACCOUNT_PATH + token));
	}
	
	private String generateVerificationToken(AppUser appUser) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setAppUser(appUser);
		verificationTokenRepository.save(verificationToken);
		return token;
	}
	
	public void verifyaccount(String token) {
		VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
				.orElseThrow(() -> new RedditCloneException("Invalid Token!"));
		fetchAndEnableAppUser(verificationToken);
	}
	
	@Transactional
	private void fetchAndEnableAppUser(VerificationToken verificationToken) {
		Long appUserId = verificationToken.getAppUser().getId();
		AppUser appUser = appUserRepository.findById(verificationToken.getAppUser().getId())
				.orElseThrow(() -> new RedditCloneException("User(" + appUserId + ") not found!"));
		appUser.setEnabled(true);
		appUserRepository.save(appUser);
	}
	
}