package com.sparta.pracspring_blog.dto;

import com.sparta.pracspring_blog.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto extends MsgResponseDto {
    private String body;
    private String username;
    private Integer likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        super();
        this.body = comment.getBody();
        this.username = comment.getUser().getUsername();
        this.likeCount = comment.getCommentLikes().size();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
