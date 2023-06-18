package com.sparta.pracspring_blog.repository;

import com.sparta.pracspring_blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    //작성 날짜 기준 내림차순 정렬
    List<Post> findAllByOrderByCreatedAtDesc();
}