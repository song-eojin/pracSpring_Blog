package com.sparta.pracspring_blog.entity;

import com.sparta.pracspring_blog.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "post")
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long postId;
    @Column(name = "title", nullable=false, length = 100) //name = "title"생략가능
    private String title;
    @Column(nullable=false, length = 500)
    private String content;
    @Column(nullable = false, length = 20)
    private String author;

    public Post(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.author = postRequestDto.getAuthor();
    }
    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.author = postRequestDto.getAuthor();
    }
}
