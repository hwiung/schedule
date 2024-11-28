package com.example.springfilter.entity;

import lombok.Getter;

@Getter
public class Post {

    private String title;

    private String contents;

    public Post(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
