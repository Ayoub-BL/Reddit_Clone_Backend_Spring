package com.ayoubbl.codingbeans.redditclonebackendspring.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Setter(value  =AccessLevel.NONE)
	private Long id;
	
	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	@Setter(value=AccessLevel.NONE)
	private Instant createdAt;
	
	@LastModifiedDate
	@Column(name = "updated_at")
	@Setter(value = AccessLevel.NONE)
	private Instant updatedAt;
	
	@NotEmpty
	private String text;
	
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "postId", referencedColumnName = "id")
	private Post post;
	
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "appUserId", referencedColumnName = "id")
	private AppUser appUser;
	
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