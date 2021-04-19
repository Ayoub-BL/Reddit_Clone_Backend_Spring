package com.ayoubbl.codingbeans.redditclonebackendspring.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.ayoubbl.codingbeans.redditclonebackendspring.exceptions.RedditCloneException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import static java.util.Date.from;

@Service
@Slf4j
public class JwtProvider {
	
	private KeyStore keyStore;
	private static final String keyStore_FILE_PATH 			= "/reddit_clone_backend_spring.jks";
	private static final String keyStore_ALIAS 				= "reddit_clone_backend_spring";
	private static final String keyStore_PASSWORD 			= "reddit_clone_backend_spring_KeyStore-Pa$$w0rd";
	// !NOTE : the default format type of the KeyStore is the string "JKS"
	private static final String keyStore_TYPE 				= KeyStore.getDefaultType();
	private static final String keyStore_AUDIENCE_PREFIX 	= "RedditCloneBackendSpringApplication_USER-";
	private static final SignatureAlgorithm keyStore_SIGNATURE_ALGORITHM = SignatureAlgorithm.RS512;

	@PostConstruct
	public void init() {
		try {
			keyStore = KeyStore.getInstance(keyStore_TYPE);
			InputStream inputStream = getClass().getResourceAsStream(keyStore_FILE_PATH);
			keyStore.load(inputStream, keyStore_PASSWORD.toCharArray());
		} catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
			String msg = "Exception occurred at " + JwtProvider.class.getSimpleName() + ".init() method!"
					+ " Exception occurred while loading keystore!";
			log.error(msg, e);
			throw new RedditCloneException(msg);
		}

	}

	public String generateToken(Authentication authentication) {
		try {
			User principal = (User) authentication.getPrincipal();
			return Jwts.builder()
					.setSubject(principal.getUsername())
					.setAudience(keyStore_AUDIENCE_PREFIX + principal.getUsername())
					.setIssuedAt(from(Instant.now()))
					.signWith(getPrivateKey(), keyStore_SIGNATURE_ALGORITHM)
					.compact();
		} catch (Exception e) {
			String msg = "Exception occurred at " + JwtProvider.class.getSimpleName() + ".generateToken() method!";
			log.error(msg, e);
			throw new RedditCloneException(msg);
		}
	}

	private PrivateKey getPrivateKey() {
		try {
			return (PrivateKey) keyStore.getKey(keyStore_ALIAS, keyStore_PASSWORD.toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
			String msg = "Exception occurred at " + JwtProvider.class.getSimpleName() + ".getPrivateKey() method!"
					+ " Exception occured while retrieving private key from keystore!";
			log.error(msg, e);
			throw new RedditCloneException(msg);
		}
	}
	
}
