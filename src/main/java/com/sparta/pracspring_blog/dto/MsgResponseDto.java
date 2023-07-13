package com.sparta.pracspring_blog.dto;

public class MsgResponseDto {
    private String message;

    public MsgResponseDto(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
