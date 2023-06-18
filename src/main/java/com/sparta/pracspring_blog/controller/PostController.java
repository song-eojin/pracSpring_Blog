package com.sparta.pracspring_blog.controller;

import com.sparta.pracspring_blog.dto.PostRequestDto;
import com.sparta.pracspring_blog.dto.PostResponseDto;
import com.sparta.pracspring_blog.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto) {
        return postService.createPost(postRequestDto);
    }
    @GetMapping("/post/all")
    public List<PostResponseDto> getPostAll() {
        return postService.getPostAll();
    }
    @GetMapping("/post/{id}")
    public Optional<PostResponseDto> getPostOne(@PathVariable Long id) {
       return postService.getPostOne(id);
    }
    @PutMapping("/post/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }
    @DeleteMapping("/post/{id}")
    public Long deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }
}
