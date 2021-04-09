package com.ayoubbl.codingbeans.redditclonebackendspring.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Setter(value=AccessLevel.NONE)
	private Long id;
	
	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	@Setter(value=AccessLevel.NONE)
	private Instant createdAt;
	
	@LastModifiedDate
	@Column(name = "updated_at")
	@Setter(value=AccessLevel.NONE)
	private Instant updatedAt;
	
	@NotBlank(message = "Post Name cannot be empty or Null")
	private String postName;
	
	@Nullable
	private String url;
	
	@Nullable
	@Lob
	private String description;
	
	private Integer voteCount;
	
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "appUserId", referencedColumnName = "id")
	private AppUser appUser;
	
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "subredditId", referencedColumnName = "id")
	private Subreddit subreddit;
	
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