package com.ayoubbl.codingbeans.redditclonebackendspring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("unused")
public class Post {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
	@NotBlank(message = "Post Name cannot be empty or Null")
	private String postName;
	
	@Nullable
	private String url;
	
	@Nullable
	@Lob
	private String description;
	
	private Integer voteCount;
	
	private Instant createdDate;
	
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "appUserId", referencedColumnName = "id")
	private AppUser appUser;
	
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "subredditId", referencedColumnName = "id")
	private Subreddit subreddit;
}