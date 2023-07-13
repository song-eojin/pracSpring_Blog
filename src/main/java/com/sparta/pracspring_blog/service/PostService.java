package com.sparta.pracspring_blog.service;

import com.sparta.pracspring_blog.dto.MsgResponseDto;
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
    @Transactional
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
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostAll() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).toList();
        //stream(): 병렬/순차 처리에 용이한 요소들의 연속으로 만들어주기 위해 Post 객체들을 스트림으로 변환 
        //map(PostResponseDto::new): PostResponseDto 의 생성자를 호출하고 각 Post 객체를 파라미터로 전달하여, Post 객체들을 PostResponseDto 객체로 변환 
        //toList(): Stream 클래스에서 지원하는 메서드로, PostResponseDto 객체를 List 로 변환
    }
    
    //(3) 선택 글 조회하기
    @Transactional(readOnly = true)
    public Optional<PostResponseDto> getPostOne(Long id) {
        return postRepository.findById(id).map(PostResponseDto::new);
    }
    
    //(4) 선택 글 수정하기
    @Transactional
    public Post updatePost(Long id, PostRequestDto postRequestDto) {

        Post post = findPost(id); //특정 id 의 게시글를 찾아서
        post.update(postRequestDto); //새롭게 요청한 데이터가 담긴 PostRequestDto 로 update
        
        return post; //update 된 특정 id 의 게시글을 반환
    }
    
    //(5) 선택 글 삭제하기
    @Transactional
    public MsgResponseDto deletePost(Long id) {
        Post post = findPost(id);
        postRepository.delete(post); //delete(): entity 를 삭제해주는 메서드
        return new MsgResponseDto("선택한 게시글 삭제를 성공했습니다.");
    }
    
    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
        //findById(id): 해당 id 의 게시물이 있으면 Optional 객체에 담아 반환하고, 존재하지 않을 경우에는 비어있는 Optional 객체 반환
        //orElseThrow(()->new ...): 게시물이 있는 경우 그대로 반환. 그리고 Optional 이 비어있는 경우 호출할 Supplier 지정. 위 코드의 경우 지정한 메세지와 함께 예외를 발생시킨다.
        //(parameter)->expression: 람다 표현식으로 주로 함수형 인터페이스를 구현할 때 사용한다. 즉, 다시말해 이 자체로 추상 메서드의 구현을 제공하는 것이다. 위 코드에서는 orElseThrow() 메서드에 람다표현식이 사용되었으며, Supplier 함수형 인터페이스를 구현하고 있다.
    }
}