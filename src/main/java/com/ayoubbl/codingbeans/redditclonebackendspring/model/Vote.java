package com.ayoubbl.codingbeans.redditclonebackendspring.model;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Vote {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Setter(value=AccessLevel.NONE)
	private Long voteId;
	
	private VoteType voteType;
	
	@NotNull
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "postId", referencedColumnName = "id")
	private Post post;
	
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "id")
	private AppUser appUser;
	
}