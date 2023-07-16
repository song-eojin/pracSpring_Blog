package com.sparta.pracspring_blog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) //Jackson 라이브러리를 사용하여 JSON 직렬화 시 특정 조건에 따라 필드를 포함하거나 제외하는 설정
public class MsgResponseDto {
    private String message;
    private Integer statusCode;

    public MsgResponseDto(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
