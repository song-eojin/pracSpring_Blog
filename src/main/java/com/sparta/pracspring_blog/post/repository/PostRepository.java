package com.sparta.pracspring_blog.post.repository;

import com.sparta.pracspring_blog.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    //작성 날짜 기준 내림차순 정렬
    List<Post> findAllByOrderByCreatedAtDesc();
}