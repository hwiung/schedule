package com.example.springfilter.controller;

import com.example.springfilter.dto.PostCreateRequestDto;
import com.example.springfilter.dto.PostResponseDto;
import com.example.springfilter.dto.PostUpdateRequestDto;
import com.example.springfilter.entity.Post;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    @PostMapping
    public PostResponseDto create(@RequestBody PostCreateRequestDto request) {
        // 로그인 여부 확인 로직

        // 생성 로직
        Post savedPost = new Post(request.getTitle(), request.getContents());

        return new PostResponseDto(savedPost.getTitle(), savedPost.getContents());
    }

    @PutMapping("/{postId}")
    public void update(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto request
    ) {
        // 로그인 여부 확인 로직
        // 수정 로직
    }

    @DeleteMapping("/{postId}")
    public void delete(@PathVariable Long postId) {
        // 로그인 여부 확인 로직
        // 삭제 로직
    }

}
