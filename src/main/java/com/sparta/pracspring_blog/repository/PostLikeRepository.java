package com.sparta.pracspring_blog.repository;

import com.sparta.pracspring_blog.entity.Post;
import com.sparta.pracspring_blog.entity.PostLike;
import com.sparta.pracspring_blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post);
    Boolean existsByUserAndPost(User user, Post post);
}
