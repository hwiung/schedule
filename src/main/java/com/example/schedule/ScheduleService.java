package com.example.schedule;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service

// 4. ScheduleService 클래스
//         -> 일정 생성, 조회, 수정, 삭제 처리를 하는 비즈니스 로직을 구현.
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 일정 생성 로직
    public ScheduleDTO createSchedule(ScheduleEntity schedule) {
        schedule.setCreatedAt(LocalDateTime.now());
        schedule.setUpdatedAt(LocalDateTime.now());
        scheduleRepository.save(schedule);

        // 비밀번호 제외 훈 DTO로 변환하여 반환
        return new ScheduleDTO(
                schedule.getId(),
                schedule.getTask(),
                schedule.getAuthor(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }

    // 전체 일정 조회 로직
    public List<ScheduleDTO> getAllSchedule(String author, LocalDateTime updatedAt) {
        List<ScheduleEntity> schedules = scheduleRepository.findAll(author, updatedAt);

        //ScheduleEntity 객체를 ScheduleDTO로 변환하여 반환
        return schedules.stream()
                .map(schedule -> new ScheduleDTO(
                        schedule.getId(),
                        schedule.getTask(),
                        schedule.getAuthor(),
                        schedule.getPassword(),
                        schedule.getCreatedAt(),
                        schedule.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    // 특정 일정 조회 로직
    public ScheduleDTO getScheduleById(Long id) {
        ScheduleEntity schedule = scheduleRepository.findById(id);
        return new ScheduleDTO(
                schedule.getId(),
                schedule.getTask(),
                schedule.getAuthor(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        ))
        .collect(Collectors.toList());
    }

    // 특정 일정 조회 로직
    public ScheduleDTO getScheduleById(Long id) {
        ScheduleEntity schedule = scheduleRepository.findById(id);
        return new ScheduleDTO(
                schedule.getId(),
                schedule.getTask(),
                schedule.getAuthor(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }
}