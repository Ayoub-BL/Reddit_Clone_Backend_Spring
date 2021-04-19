package com.ayoubbl.codingbeans.redditclonebackendspring.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(indexes = {
        @Index(name = "uniqueUsername", columnList = "username", unique = true),
        @Index(name = "uniqueEmail", columnList = "email", unique = true)
      })
public class AppUser {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private Long id;
	
	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	@Setter(value = AccessLevel.NONE)
	private Instant createdAt;
	
	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	@Setter(value = AccessLevel.NONE)
	private Instant updatedAt;
	
	@Column(nullable = false)
	private boolean enabled;
	
	@NotBlank(message = "Username is required")
	@Column(nullable = false)
	private String username;
	
	@Email
	@NotBlank(message = "Email is required")
	@Column(nullable = false)
	private String email;
	
	@NotBlank(message = "Password is required")
	@Column(nullable = false)
	private String password;
	
	@PrePersist
	protected void prePersist() {
		if (this.createdAt == null) createdAt = Instant.now();
		if (this.updatedAt == null) updatedAt = Instant.now();
	}
	
	@PreUpdate
	protected void preUpdate() {
		this.updatedAt = Instant.now();
	}
	
}