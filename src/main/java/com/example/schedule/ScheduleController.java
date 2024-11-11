package com.example.schedule;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

// @RestController 어노테이션:
//  - 클래스가 RESTful 웹 서비스의 컨트롤러 역할을 하며, 클라이언트의 요청을 처리하고 JSON 형식으로 응답을 반환하게 함.
@RestController

// @RequestMapping 어노테이션:
//  - 클래스의 엔드포인트 기본 경로를 "/api/schedules" 경로로 함.
@RequestMapping("/api/schedules")

// 5. ScheduleController 클래스:
//  - 클라이언트의 요구 사항을 각 엔드포인트에서 처리하여 ScheduleService 메서드를 통해 응답을 반환함.
//  - 엔드포인트: 클라이언트와 서버를 연결하는 URL 경로를 의미함(주요 엔드포인트: 일정 생성, 전체 일정 조회, 특정 일정 조회).
public class ScheduleController {

    //  ScheduleService 주입을 위한 ScheduleService 필드(final 선언으로 재할당 불가)
    private final ScheduleService scheduleService;

    // 생성자: ScheduleService 의존성을 주입 받아 초기화.
    //  - 외부에서 ScheduleRepository 객체를 전달 받아 사용함.
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 생성 엔드포인트:
    //  - 클라이언트에게 데이터를 받아서 일정을 생성함.
    //  - HTTP 메서드: POST
    //  - URL: /api/schedules
    //  - RequestBody: 클라이언트가 보낸 JSON 형식의 데이터를 ScheduleEntity 객체로 변환하여 받아들임.
    //  - 반환값: 생성된 ScheduleDTO 객체를 201(CreatedAt) 상태 코드와 함께 반환.
    @PostMapping
    public ResponseEntity<ScheduleDTO> createSchedule(@RequestBody ScheduleEntity schedule) {
        // ScheduleService를 통해 일정 생성.
        ScheduleDTO createdSchedule = scheduleService.createSchedule(schedule);
        // 201 Created 상태 코드로 응답 반환.
        return ResponseEntity.status(201).body(createdSchedule);
    }

    // 전체 일정 조회 엔드포인트:
    //  - 전체 일정을 조회하며, 작성자와 수정일을 조건으로 필터링함.
    //  - HTTP 메서드: GET
    //  - URL: /api/schedules
    //  - Query Parameters:
    //      - author: 필터링할 작성자 이름.
    //      - updatedAt: 필터링할 수정일.
    //  - 반환값: 조회된 ScheduleDTO 객체를 '200 OK' 상태 코드와 함께 반환함.
    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules(
            @RequestParam(required = false) String author,      // 작성자 필터링을 위한 파라미터.
            @RequestParam(required = false) String updatedAt        // 수정일 필터링을 위한 파라미터.
            // LocalDateTime updatedAt 대신 String updatedAt을 사용.
            //  - 클라이언트가 날짜를 더 편하게 전달할 수 있게끔 문자열로 받음(클라이언트가 'yyyy-MM-dd'T'hh:mm:ss' 형식의 문자열로 보내게끔 했음).
    ) {
        LocalDateTime parsedUpdatedAt = null;

        // updatedAt이 null이 아닌 경우, 문자열을 LocalDateTime으로 변환.
        try {
            if (updatedAt != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                // updatedAt 문자열을 LocalDateTime으로 변환하기 위해 DateTimeFormatter를 사용.

                parsedUpdatedAt = LocalDateTime.parse(updatedAt, formatter);
            }
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request 반환
        }

        // 필터링된 일정 목록을 ScheduleDTO로 반환.
        List<ScheduleDTO> schedules = scheduleService.getAllSchedules(author, parsedUpdatedAt);
        // '200 OK' 상태 코드로 응답 반환
        return ResponseEntity.ok(schedules);
    }

    // 특정 일정 조회 엔드포인트:
    //  - 일정 ID를 사용하여 특정 일정을 조회함.
    //  - HTTP 메서드: GET
    //  - URL: /api/schedules/{id}
    //  - Path Parameter:
    //      - id: 조회할 일정의 고유 ID
    //  - 반환값: 조회된 ScheduleDTO 객체를 '200 OK' 상태 코드와 함께 반환함.
    //      - 일정이 존재하지 않으면 '404 Not Found' 상태 코드와 함께 반환함.
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDTO> getScheduleById(@PathVariable Long id) {
        // 서비스 계층에서 특정 일정 조회
        ScheduleDTO schedule = scheduleService.getScheduleById(id);
        // 일정이 존재할 경우 '200 OK'로 응답하고, 없을 경우엔 '404 Not Found'로 응답함.
        return schedule != null ? ResponseEntity.ok(schedule) : ResponseEntity.notFound().build();
    }
}