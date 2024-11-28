package com.example.springfilter.dto;

import lombok.Getter;

@Getter
public class PostCreateRequestDto {
    private final String title;

    private final String contents;

    public PostCreateRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
