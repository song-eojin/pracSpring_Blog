package com.sparta.pracspring_blog.post.entity;

import com.sparta.pracspring_blog.comment.entity.Comment;
import com.sparta.pracspring_blog.post.dto.PostRequestDto;
import com.sparta.pracspring_blog.common.entity.Timestamped;
import com.sparta.pracspring_blog.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post extends Timestamped { // 상속받은 TimeStamped의 필드도 자동으로 Post 테이블의 필드로 추가된다.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable=false, length = 100) //name = "title"생략가능
    private String title;

    @Column(nullable=false, length = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    public Post(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }

    public void update(PostRequestDto requestDto) {
        this.title =requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}