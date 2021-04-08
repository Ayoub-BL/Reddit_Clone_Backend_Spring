package com.ayoubbl.codingbeans.redditclonebackendspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ayoubbl.codingbeans.redditclonebackendspring.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{}
