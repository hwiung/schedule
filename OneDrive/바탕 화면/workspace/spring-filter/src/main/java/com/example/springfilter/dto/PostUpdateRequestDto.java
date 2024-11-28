package com.example.springfilter.dto;

import lombok.Getter;

@Getter
public class PostUpdateRequestDto {

    private final String title;

    public PostUpdateRequestDto(String title) {
        this.title = title;
    }
}
