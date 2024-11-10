package com.example.schedule;

import java.time.LocalDateTime;

// 2. ScheduleDTO 클래스:
//  - 데이터 전송 시 사용되는 DTO 클래스.
//  - 데이터 반환 시, 특정 정보를 빼고 전달해야 하는 경우에 사용함. 현재의 경우엔 비밀번호를 제외하고 반환할 것임.

public class ScheduleDTO {
    // 일정의 고유 ID를 저장하는 필드
    private Long id;

    // 일정의 제목, 내용을 저장하는 필드
    private String task;

    // 일정의 작성자의 이름을 저장하는 필드
    private String author;

    // 일정을 처음 생성한 날짜, 시간대를 저장하는 필드
    private LocalDateTime createdAt;

    // 일정을 마지막으로 수정한 날짜, 시간대를 저장하는 필드
    private LocalDateTime updatedAt;

    // 전체 필드를 초기화하는 생성자:
    //  - 모든 필드를 초기화하여 ScheduledDTO 객체를 생성.
    //  - 각 필드를 초기화하면, 이후 Setter 메서드를 호출 없이도 초기화된 값을 사용할 수 있어 객체 생성을 한 번에 완료할 수 있음.
    public ScheduleDTO(Long id, String task, String author, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.task = task;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter 메서드만 생성:
    //  - 필드 값을 반환(변경 방지를 위해 Setter는 제외).
    //  - DTO는 데이터 반환용으로 사용하는 것이기 때문에, Getter 메서드만 사용할 것임.

    // 일정의 고유 ID를 반환하는 메서드
    public Long getId() {
        return id;
    }

    // 일정의 제목, 내용을 반환하는 메서드
    public String getTask() {
        return task;
    }

    // 일정의 작성자의 이름을 반환하는 메서드
    public String getAuthor() {
        return author;
    }

    // 일정 생성 날짜, 시간을 반환하는 메서드
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // 일정의 마지막 수정 날짜, 시간을 반환하는 메서드
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
