package com.ayoubbl.codingbeans.redditclonebackendspring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@SuppressWarnings("unused")
public class Subreddit {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
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
}