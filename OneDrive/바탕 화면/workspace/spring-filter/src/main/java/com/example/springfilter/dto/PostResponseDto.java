package com.example.springfilter.dto;

import lombok.Getter;

@Getter
public class PostResponseDto {

    private final String title;

    private final String contents;

    public PostResponseDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
