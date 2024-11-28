package com.example.schedule;

import java.time.LocalDateTime;

// 1. ScheduleEntity 클래스:
//  - 데이터베이스의 schedule 테이블과 매핑됨.
//  - 일정에 대한 정보를 저장하고 데이터베이스와의 상호작용을 위한 객체.
public class ScheduleEntity {

    //각 일정의 고유 ID를 나타내는 필드(자동 증가)
    private Long id;

    // 일정의 제목과 내용을 저장하는 필드
    private String task;

    // 일정의 작성자의 이름을 저장하는 필드
    private String author;

    // 일정 수정 및 삭제 시 사용될 비밀번호를 저장하는 필드
    private String password;

    // 일정을 처음 생성한 날짜, 시간대를 저장하는 필드
    private LocalDateTime createdAt;

    // 일정을 마지막으로 수정한 날짜, 시간대를 저장하는 필드
    private LocalDateTime updatedAt;

    // 기본 생성자
    //  아무 필드 없이 ScheduleEntity 객체를 생성할 때 사용
    public ScheduleEntity() {
    }

    // 전체 필드를 초기화하는 생성자:
    //  - 모든 필드를 초기화하여 ScheduleEntity 객체를 생성.
    //  - 각 필드를 초기화하면, 이후 Setter 메서드를 호출 없이도 초기화된 값을 사용할 수 있어 객체 생성을 한 번에 완료할 수 있음.
    public ScheduleEntity(Long id, String task, String author, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.task = task;
        this.author = author;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter와 Setter 메서드:
    //  - 필드 값을 반환 및 설정
    //  - 외부가 아닌, 내부에서 메서드를 통해 필드 값을 가져오거나 설정할 수 있음.

    // 일정의 고유 ID를 반환하는 Getter 메서드
    public Long getId() {
        return id;
    }

    // 일정의 고유 ID를 설정하는 Setter 메서드
    public void setId(Long id) {
        this.id = id;
    }

    // 일정의 제목, 내용을 반환하는 Getter 메서드
    public String getTask() {
        return task;
    }

    // 일정의 제목, 내용을 설정하는 Setter 메서드
    public void setTask(String task) {
        this.task = task;
    }

    // 일정의 작성자의 이름을 반환하는 Getter 메서드
    public String getAuthor() {
        return author;
    }

    // 일정의 작성자의 이름을 설정하는 Setter 메서드
    public void setAuthor(String author) {
        this.author = author;
    }

    // 일정 수정/삭제 시 사용할 비밀번호를 반환하는 Getter 메서드
    public String getPassword() {
        return password;
    }

    // 일정 수정/삭제 시 사용할 비밀번호를 설정하는 Setter 메서드
    public void setPassword(String password) {
        this.password = password;
    }

    // 일정의 생성 날짜, 시간을 반환하는 Getter 메서드
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // 일정의 생성 날짜, 시간을 설정하는 Setter 메서드
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // 일정의 마지막 수정 날짜, 시간을 반환하는 Getter 메서드
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // 일정의 마지막 수정 날짜, 시간을 설정하는 Setter 메서드
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}