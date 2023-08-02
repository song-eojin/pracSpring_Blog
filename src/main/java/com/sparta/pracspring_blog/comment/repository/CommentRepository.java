package com.sparta.pracspring_blog.comment.repository;

import com.sparta.pracspring_blog.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}