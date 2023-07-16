package com.sparta.pracspring_blog.repository;

import com.sparta.pracspring_blog.entity.Comment;
import com.sparta.pracspring_blog.entity.CommentLike;
import com.sparta.pracspring_blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
    Boolean existsByUserAndComment(User user, Comment comment);
}
