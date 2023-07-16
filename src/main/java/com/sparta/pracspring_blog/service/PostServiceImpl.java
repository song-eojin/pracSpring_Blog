package com.sparta.pracspring_blog.service;

import com.sparta.pracspring_blog.dto.MsgResponseDto;
import com.sparta.pracspring_blog.dto.PostRequestDto;
import com.sparta.pracspring_blog.dto.PostResponseDto;
import com.sparta.pracspring_blog.entity.Post;
import com.sparta.pracspring_blog.entity.PostLike;
import com.sparta.pracspring_blog.entity.User;
import com.sparta.pracspring_blog.repository.PostLikeRepository;
import com.sparta.pracspring_blog.repository.PostRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    //(1) 글 생성하기
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, User user) {
        //RequestDto -> Entity
        Post post = new Post(postRequestDto);

        //Entity 를 DB 저장
        Post savaPost = postRepository.save(post);

        //Entity -> ResponseDto
        PostResponseDto postResponseDto = new PostResponseDto(post);

        return postResponseDto;
    }

    //(2) 전체 글 조회하기
    @Override
    @Transactional(readOnly = true)
    public PostResponseDto getPosts() {
        List<PostResponseDto> postList = postRepository.findAll().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

        return new PostResponseDto(postList);
        //stream(): 병렬/순차 처리에 용이한 요소들의 연속으로 만들어주기 위해 Post 객체들을 스트림으로 변환 
        //map(PostResponseDto::new): PostResponseDto 의 생성자를 호출하고 각 Post 객체를 파라미터로 전달하여, Post 객체들을 PostResponseDto 객체로 변환 
        //toList(): Stream 클래스에서 지원하는 메서드로, PostResponseDto 객체를 List 로 변환
    }
    
    //(3) 선택 글 조회하기
    @Transactional(readOnly = true)
    public PostResponseDto getPostById(Long id) {
        Post post = findPost(id);
        return new PostResponseDto(post);
    }
    
    //(4) 선택 글 수정하기
    @Override
    @Transactional
    public PostResponseDto updatePost(Post post, PostRequestDto requestDto, User user) {

        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());

        return new PostResponseDto(post);
    }
    
    //(5) 선택 글 삭제하기
    @Override
    public void deletePost(Post post, User user) {
        postRepository.delete(post);
    }

    //(6) 좋아요 중복 처리
    @Transactional
    public void likePost(Long id, User user) {
        Post post = findPost(id);

        // 아래 조건 코드와 동일
        // if (postLikeRepository.findByUserAndPost(user, post).isPresent()) {
        if (postLikeRepository.existsByUserAndPost(user, post)) {
            throw new DuplicateRequestException("이미 좋아요 한 게시글 입니다.");
        } else {
            PostLike postLike = new PostLike(user, post);
            postLikeRepository.save(postLike);
        }
    }

    //(7) 좋아요 취소하기
    @Override
    @Transactional
    public void deleteLikePost(Long id, User user) {
        Post post = findPost(id);
        Optional<PostLike> postLikeOptional = postLikeRepository.findByUserAndPost(user, post);
        if (postLikeOptional.isPresent()) {
            postLikeRepository.delete(postLikeOptional.get());
        } else {
            throw new IllegalArgumentException("해당 게시글에 취소할 좋아요가 없습니다.");
        }
    }

    @Override
    public Post findPost(long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
        //findById(id): 해당 id 의 게시물이 있으면 Optional 객체에 담아 반환하고, 존재하지 않을 경우에는 비어있는 Optional 객체 반환
        //orElseThrow(()->new ...): 게시물이 있는 경우 그대로 반환. 그리고 Optional 이 비어있는 경우 호출할 Supplier 지정. 위 코드의 경우 지정한 메세지와 함께 예외를 발생시킨다.
        //(parameter)->expression: 람다 표현식으로 주로 함수형 인터페이스를 구현할 때 사용한다. 즉, 다시말해 이 자체로 추상 메서드의 구현을 제공하는 것이다. 위 코드에서는 orElseThrow() 메서드에 람다표현식이 사용되었으며, Supplier 함수형 인터페이스를 구현하고 있다.
    }
}