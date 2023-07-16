package com.sparta.pracspring_blog.controller;

import com.sparta.pracspring_blog.dto.MsgResponseDto;
import com.sparta.pracspring_blog.dto.PostRequestDto;
import com.sparta.pracspring_blog.dto.PostResponseDto;
import com.sparta.pracspring_blog.entity.Post;
import com.sparta.pracspring_blog.security.UserDetailsImpl;
import com.sparta.pracspring_blog.service.PostServiceImpl;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostServiceImpl postServiceImpl;

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostRequestDto requestDto) {
        PostResponseDto result = postServiceImpl.createPost(requestDto, userDetails.getUser());

        return ResponseEntity.status(201).body(result);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponseDto> getPosts() {
        PostResponseDto result = postServiceImpl.getPosts();

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        PostResponseDto result = postServiceImpl.getPostById(id);

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<MsgResponseDto> updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        try {
            Post post = postServiceImpl.findPost(id);
            postServiceImpl.deletePost(post, userDetails.getUser());
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new MsgResponseDto("작성자만 수정 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.ok().body(new MsgResponseDto("게시글 삭제를 성공하셨습니다.", HttpStatus.OK.value()));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<MsgResponseDto> deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        try {
            Post post = postServiceImpl.findPost(id);
            postServiceImpl.deletePost(post, userDetails.getUser());
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new MsgResponseDto("작성자만 삭제 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.ok().body(new MsgResponseDto("게시글 삭제 성공", HttpStatus.OK.value()));
    }

    @PostMapping("/posts/{id}/like")
    public ResponseEntity<MsgResponseDto> likePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        try {
            postServiceImpl.likePost(id, userDetails.getUser());
        } catch (DuplicateRequestException e) {
            return ResponseEntity.badRequest().body(new MsgResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MsgResponseDto("게시글 좋아요 성공", HttpStatus.ACCEPTED.value()));
    }
    @DeleteMapping("/posts/{id}/like")
    public ResponseEntity<MsgResponseDto> deleteLikePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        try {
            postServiceImpl.deleteLikePost(id, userDetails.getUser());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MsgResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MsgResponseDto("게시글 좋아요 취소 성공", HttpStatus.ACCEPTED.value()));
    }
}