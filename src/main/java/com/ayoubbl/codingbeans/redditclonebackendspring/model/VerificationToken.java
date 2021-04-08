package com.ayoubbl.codingbeans.redditclonebackendspring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuppressWarnings("unused")
public class VerificationToken {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
	private String token;
	
	private Instant expiryDate;
	
	@OneToOne(fetch = LAZY)
	private AppUser appUser;
}