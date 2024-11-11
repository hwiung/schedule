package com.example.schedule;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

// @Service 어노테이션:
//  - Bean(객체 관리와 주입을 자동화)으로 등록하고, 비즈니스 로직을 수행하는 서비스 계층으로 표시함.
@Service

// 4. ScheduleService 클래스:
//  - 일정 생성, 조회, 수정, 삭제 처리를 하는 비즈니스 로직을 구현.
//  - 데이터 접근을 위해 ScheduleRepositery를 사용하고, 필요한 데이터는 ScheduleDTO로 변환하여 반환함.
public class ScheduleService {

    // CRUD 작업을 수행하기 위한 ScheduleRepository 필드(final 선언으로 재할당 불가)
    private final ScheduleRepository scheduleRepository;

    // 생성자: ScheduleRepository 주입(@Service 어노테이션을 통해 스프링 빈으로 등록됐기 때문에 자동으로 의존성을 주입함).
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 일정 생성 메서드:
    //  - 일정 생성, 수정, 저장을 수행하는 비즈니스 로직임.
    //  - 반환값: ScheduleEntity 객체를 ScheduleDTO 객체로 변환하여 반환함.
    //  - 반환 시 비밀번호를 포함하지 않음.
    public ScheduleDTO createSchedule(ScheduleEntity schedule) {

        // 현재 날짜, 시간대를 생성일 및 수정일로 설정함.
        schedule.setCreatedAt(LocalDateTime.now());
        schedule.setUpdatedAt(LocalDateTime.now());

        // ScheduleRepository를 통해 데이터베이스에 객체를 저장함.
        scheduleRepository.save(schedule);

        // ScheduleEntity 객체를 ScheduleDTO로 변환 후 반환함(이때, 비밀번호는 제외함).
        return new ScheduleDTO(
                schedule.getId(),
                schedule.getTask(),
                schedule.getAuthor(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }

    // 전체 일정 조회 메서드:
    //  - 작성자와 수정일을 조건으로 전체 일정에서 해당 데이터만 필터링하여 조회함.
    //  - 반환값: 조회된 Schedule 객체를 ScheduleDTO로 변환하여 반환함.
    //  - 필터링 옵션:
    //      - author: 특정 작성자로 필터링
    //      - updatedAt: 특정 수정일자로 필터링

    // 일정 목록 조회(ScheduleEntity 반환)
    public List<ScheduleDTO> getAllSchedules(String author, LocalDateTime updatedAt) {
        List<ScheduleEntity> schedules = scheduleRepository.findAll(author, updatedAt);

        //ScheduleEntity 객체를 ScheduleDTO로 변환하여 반환
        return schedules.stream()
                .map(schedule -> new ScheduleDTO(
                        schedule.getId(),
                        schedule.getTask(),
                        schedule.getAuthor(),
                        schedule.getCreatedAt(),
                        schedule.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    // 특정 일정 조회 메서드:
    //  - 일정 ID를 통해 특정 일정을 조회하고 ScheduleDTO로 변환하여 반환함.
    //  - 예외 처리: 특정 일정이 존재하지 않는 경우 NoSuchElementException을 던짐(안전성+).
    public ScheduleDTO getScheduleById(Long id) {
        //  - 일정 ID로 특정 일정 조회.
        ScheduleEntity schedule = scheduleRepository.findById(id);

        //  - 특정 일정이 존재하지 않는 경우 예외 발생(NoSuchElementException).
        if (schedule == null) {
            throw new NoSuchElementException("일정이 존재하지 않습니다. ID: " + id);
        }

        //  - 조회된 ScheduleEntity 객체를 ScheduleDTO로 변환하여 반환함(이때 비밀번호는 제외됨).
        return new ScheduleDTO(
                schedule.getId(),
                schedule.getTask(),
                schedule.getAuthor(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }

    // 일정 수정 메서드: 클라이언트가 전달한 일정 ID와 데이터로 일정을 수정하는 로직
    // - HTTP 메서드: PUT
    // - URL: /api/schedules/{id}
    //  - RequestBody: 수정할 일정의 내용, 작성자 이름, 비밀번호를 포함하는 ScheduleEntity 객체
    //  - 반환값: 수정된 ScheduleDTO 객체를 200(OK) 상태 코드와 함께 반환, 비밀번호가 일치하지 않거나 일정이 없으면 403 또는 404 반환
    public ScheduleDTO updateSchedule(Long id, ScheduleEntity schedule) {
        // 기존 일정 조회 및 검증
        ScheduleEntity existingSchedule = scheduleRepository.findById(id);
        if (existingSchedule == null || !existingSchedule.getPassword().equals(schedule.getPassword())) {
            throw new NoSuchElementException("일정이 존재하지 않거나 비밀번호가 일치하지 않습니다.");
        }

        // 업데이트할 항목만 변경
        existingSchedule.setTask(schedule.getTask());
        existingSchedule.setAuthor(schedule.getAuthor());
        existingSchedule.setUpdatedAt(LocalDateTime.now());
        scheduleRepository.update(existingSchedule.getId(), existingSchedule.getTask(), existingSchedule.getAuthor(), existingSchedule.getPassword());
        // 데이터베이스 업데이트 후 DTO 변환하여 반환
        return new ScheduleDTO(
                existingSchedule.getId(),
                existingSchedule.getTask(),
                existingSchedule.getAuthor(),
                existingSchedule.getCreatedAt(),
                existingSchedule.getUpdatedAt()
        );
    }

    // 일정 삭제 메서드: 일정 ID를 사용하여 특정 일정을 삭제함
    //  - HTTP 메서드: DELETE
    //  - URL: /api/schedules/{id}
    //  - 반환값: 204 No Content 상태 코드 반환, 비밀번호가 일치하지 않거나 일정이 없으면 403 또는 404 반환
    public void deleteSchedule(Long id, String password) {
        int rowsAffected = scheduleRepository.delete(id, password);
        if (rowsAffected == 0) {
            throw new NoSuchElementException("일정이 존재하지 않거나 비밀번호가 일치하지 않습니다.");
        }
    }
}


