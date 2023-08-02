package com.sparta.pracspring_blog.post.service;

import com.sparta.pracspring_blog.post.dto.PostRequestDto;
import com.sparta.pracspring_blog.post.dto.PostResponseDto;
import com.sparta.pracspring_blog.post.entity.Post;
import com.sparta.pracspring_blog.user.entity.User;
import com.sparta.pracspring_blog.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    //(1) 글 생성하기
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, User user) {
        Post post = new Post(postRequestDto);
        post.setUser(user);

        postRepository.save(post);

        return new PostResponseDto(post);
    }

    //(2) 전체 글 조회하기
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        List<PostResponseDto> postList = postRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
        //stream(): 병렬/순차 처리에 용이한 요소들의 연속으로 만들어주기 위해 Post 객체들을 스트림으로 변환
        //map(PostResponseDto::new): PostResponseDto 의 생성자를 호출하고 각 Post 객체를 파라미터로 전달하여, Post 객체들을 PostResponseDto 객체로 변환
        //toList(): Stream 클래스에서 지원하는 메서드로, PostResponseDto 객체를 List 로 변환

        return postList;
    }
    
    //(3) 선택 글 조회하기
    @Transactional(readOnly = true)
    public PostResponseDto getPostById(Long id) {
        Post post = findPost(id);
        return new PostResponseDto(post);
    }
    
    //(4) 선택 글 수정하기
    @Transactional
    public Post updatePost(Post post, PostRequestDto postRequestDto) {
        post.update(postRequestDto); //새롭게 요청한 데이터가 담긴 PostRequestDto 로 update
        
        return post; //update 된 특정 id 의 게시글을 반환
    }
    
    //(5) 선택 글 삭제하기
    @Transactional
    public void deletePost(Post post, User user) {
        if (!post.getUser().equals(user)) {
            throw new RejectedExecutionException();
        }

        postRepository.delete(post);
    }
    public Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
        //findById(id): 해당 id 의 게시물이 있으면 Optional 객체에 담아 반환하고, 존재하지 않을 경우에는 비어있는 Optional 객체 반환
        //orElseThrow(()->new ...): 게시물이 있는 경우 그대로 반환. 그리고 Optional 이 비어있는 경우 호출할 Supplier 지정. 위 코드의 경우 지정한 메세지와 함께 예외를 발생시킨다.
        //(parameter)->expression: 람다 표현식으로 주로 함수형 인터페이스를 구현할 때 사용한다. 즉, 다시말해 이 자체로 추상 메서드의 구현을 제공하는 것이다. 위 코드에서는 orElseThrow() 메서드에 람다표현식이 사용되었으며, Supplier 함수형 인터페이스를 구현하고 있다.
    }
}