package com.sparta.pracspring_blog.post.controller;

import com.sparta.pracspring_blog.common.dto.MsgResponseDto;
import com.sparta.pracspring_blog.post.dto.PostRequestDto;
import com.sparta.pracspring_blog.post.dto.PostResponseDto;
import com.sparta.pracspring_blog.post.entity.Post;
import com.sparta.pracspring_blog.common.security.UserDetailsImpl;
import com.sparta.pracspring_blog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostRequestDto postRequestDto) {
        PostResponseDto result = postService.createPost(postRequestDto, userDetails.getUser());

        return ResponseEntity.status(201).body(result);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponseDto> getPosts() {
        PostResponseDto result = new PostResponseDto(postService.getPosts());

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        PostResponseDto result = postService.getPostById(id);

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<MsgResponseDto> updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        try {
            Post post = postService.findPost(id);
            postService.updatePost(post, postRequestDto);
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new MsgResponseDto("작성자만 수정 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.ok().body(new MsgResponseDto("게시글 삭제를 성공하셨습니다.", HttpStatus.OK.value()));
    }
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<MsgResponseDto> deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        try {
            Post post = postService.findPost(id);
            postService.deletePost(post, userDetails.getUser());
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new MsgResponseDto("작성자만 삭제 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.ok().body(new MsgResponseDto("게시글 삭제 성공", HttpStatus.OK.value()));
    }
}