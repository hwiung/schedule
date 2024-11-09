package com.example.schedule;

import java.time.LocalDateTime;

// 1. ScheduleEntity 클래스
//          -> 일정에 대한 정보를 저장
public class ScheduleEntity {
    private Long id;
    private String task;
    private String author;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 기본 생성자
    public ScheduleEntity() {
    }

    // 전체 필드를 초기화하는 생성자
    public ScheduleEntity(Long id, String task, String author, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.task = task;
        this.author = author;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

//          1-1. Getter와 Setter 메서드
//               -> 필드 값을 반환 및 설정
public Long getId() { return id; }
public void setId(Long id) { this.id = id; }

public String getTask() { return task; }
public void setTask(String task) { this.task = task; }

public String getAuthor() { return author; }
public void setAuthor(String author) { this.author = author; }

public String getPassword() { return password; }
public void setPassword(String password) { this.password = password; }

public LocalDateTime getCreatedAt() { return createdAt; }
public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

public LocalDateTime getupdatedAt() { return updatedAt;}
public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }