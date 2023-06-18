package com.sparta.pracspring_blog.service;

import com.sparta.pracspring_blog.dto.PostRequestDto;
import com.sparta.pracspring_blog.dto.PostResponseDto;
import com.sparta.pracspring_blog.entity.Post;
import com.sparta.pracspring_blog.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    //(1) 글 생성하기
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        //RequestDto -> Entity
        Post post = new Post(postRequestDto);

        //Entity 를 DB 저장
        Post savaPost = postRepository.save(post);

        //Entity -> ResponseDto
        PostResponseDto postResponseDto = new PostResponseDto(post);

        return postResponseDto;
    }
    //(2) 전체 글 조회하기
    public List<PostResponseDto> getPostAll() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).toList();
    }
    //(3) 선택 글 조회하기
    public Optional<PostResponseDto> getPostOne(Long id) {
        Optional<PostResponseDto> postOne = postRepository.findById(id).map(PostResponseDto::new);
        return postOne;
    }
    //(4) 선택 글 수정하기
    @Transactional
    public Post updatePost(Long id, PostRequestDto postRequestDto) {

        Post post = findPost(id);//?
        post.update(postRequestDto);
        return post;
    }
    //(5) 선택 글 삭제하기
    public String deletePost(Long id) {
        Post post = findPost(id);
        postRepository.delete(post);
        return "선택한 게시글 삭제를 성공했습니다.";
    }
    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }
}
