package com.ayoubbl.codingbeans.redditclonebackendspring.model;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.util.Date.from;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VerificationToken {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Setter(value = AccessLevel.NONE)
	@Column(nullable = false)
	private Long id;
	
	@NotBlank(message = "Token expiry date is required")
	@Column(name = "expiry_date", nullable = false, updatable = false)
	@Setter(value = AccessLevel.NONE)
	private Date expiryDate;
	
	@NotBlank(message = "token is required")
	@Column(nullable = false)
	private String token;
	
	@OneToOne(fetch = LAZY)
	private AppUser appUser;
	
	@Transient
	@Setter(value = AccessLevel.NONE)
	@Getter(value = AccessLevel.NONE)
	private static final long tokenExpiration_InMillis = 24*3600*1000; // in milliseconds.
	
	@PrePersist
	protected void prePersist() {
		if (this.expiryDate == null) expiryDate = from(Instant.now().plusMillis(tokenExpiration_InMillis));
	}
	
}