package com.example.schedule;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")

// 5. ScheduleController 클래스
//         -> 클라이언트의 요구 사항을 각 엔드포인트에서 처리하여 응답을 반환한다.
public class ScheduleController {
    this.scheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 생성 엔드포인트
    @PostMapping
    public ResponseEntity<ScheduleDTO> createSchedule(@RequestBody ScheduleEntity schedule) {
        ScheduleDTO createdSchedule = scheduleService.createdSchedule(schedule);
        return ResponseEntity.status(201).body(createdSchedule);
    }

    // 전체 일정 조회 엔드포인트
    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules(
            @RequestParam(required = false) String author,
            @RequestParam(required = false)LocalDateTime updatedAt
    ) {
        List<ScheduleDTO> schedules = scheduleService.getAllSchedules(author, updatedAt);
        return ResponseEntity.ok(schedules);
    }

    // 특정 일정 조회 엔드포인트
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDTO> getScheduleById(@PathVariable Long id) {
        ScheduleDTO schedule = scheduleService.getScheduleById(id);
        return schedule != null ? ResponseEntity.ok(schedule) : ResponseEntity.notFound().build();
    }
}