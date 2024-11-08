package com.example.schedule;

import java.time.LocalDateTime;

// 2. ScheduleDTO 클래스
//        -> 데이터 반환 시, 비밀번호는 제외하고 반환함.
public class ScheduleDTO {
    private Long id;
    private String task;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 생성자
    public ScheduleDTO(Long id, String task, String author, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.task = task;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter만 생성 (변경 방지를 위해 setter는 제외)
    public Long getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
