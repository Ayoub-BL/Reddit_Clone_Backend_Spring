package com.ayoubbl.codingbeans.redditclonebackendspring.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class VerificationToken {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Setter(value=AccessLevel.NONE)
	private Long id;
	
	private Instant expiryDate;
	
	private String token;
	
	@OneToOne(fetch = LAZY)
	private AppUser appUser;
	
}