package com.ayoubbl.codingbeans.redditclonebackendspring.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuppressWarnings("unused")
public class Comment {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
	@NotEmpty
	private String text;
	
	private Instant createdDate;
	
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "postId", referencedColumnName = "id")
	private Post post;
	
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "appUserId", referencedColumnName = "id")
	private AppUser appUser;
}