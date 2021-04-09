package com.ayoubbl.codingbeans.redditclonebackendspring.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Subreddit {
	
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
	
	@NotBlank(message = "Community name is required")
	private String name;
	
	@NotBlank(message = "Description is required")
	@Lob
	private String description;
	
	private Instant createdDate;
	
	@OneToMany(fetch = LAZY)
	private List<Post> posts;
	
	@ManyToOne(fetch = LAZY)
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