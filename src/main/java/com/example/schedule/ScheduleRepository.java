package com.example.schedule;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

// Repository 어노테이션:
//  - Bean(객체 관리와 주입을 자동화)으로 등록하고, 데이터 접근 계층으로 표시함.
//  - 스프링 데이터 접근 예외로 변환하여 일관된 예외 처리가 가능함.
@Repository

// 3. ScheduleRepository 클래스: JdbcTemplate을 활용하여 자바에서 CRUD를 구현.
//      (Create: 일정 생성 -> INSERT 쿼리를 사용하여 데이터베이스에 데이터를 추가).
//      (Read: 전체 일정 조회 및 선택 일정 조회 -> SELECT 쿼리를 사용. 이때, RowMapper를 이용하여 데이터베이스의 데이터를 각 클래스의 객체로 Mapping한다).
//      (Update: 선택 일정 수정 -> UPDATE 쿼리를 사용하여 id와 password를 확인 후, 데이터베이스에서 데이터를 수정함).
//      (Delete: 선택 일정 삭제 -> DELETE 쿼리를 사용하여 id와 password를 확인 후, 데이터베이스에서 데이터를 삭제함).
public class ScheduleRepository {

    // 데이터베이스 접근을 위한 JdbcTemplate 필드(final 선언으로 재할당 불가)
    private final JdbcTemplate jdbcTemplate;

    // 생성자: JdbcTemplate 주입(Spring에서 JdbcTemplate을 주입 받아 사용).
    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // 일정 저장 메서드:
    //  - ScheduleEntity 객체를 데잍베이스의 schedule 테이블에 저장함.
    //  - 파라미터로 전달된 ScheduleEntity 객체의 필드 값을 사용하여 INSERT 쿼리를 실행하고, 데이터를 추가함.
    public int save(ScheduleEntity schedule) {
        String sql = "INSERT INTO schedule (task, author, password, createdAt, updatedAt) VALUES ( ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                schedule.getTask(),
                schedule.getAuthor(),
                schedule.getPassword(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }

    // 전체 일정 조회 메서드:
    //  - 데이터베이스에서 전체 일정을 조회하고, 조건에 맞는 일정 목록을 반환함.
    //  - 파라미터:
    //      - author: 특정 작성자를 필터링하는 조건
    //      - updatedAt: 특정 수정일을 필터링하는 조건
    //  - 동작:
    //      - 쿼리 작성 시 author와 updatedAt이 null이 아니면 해당 조건을 추가하여 필터링함.
    //      - 조건이 없으면 전체 일정을 조회하고, 수정일 기준으로 내림차순 정렬함.
    //  - 반환값: ScheduleEntity 객체 리스트
    public List<ScheduleEntity> findAll(String author, LocalDateTime updatedAt) {
        String sql = "SELECT * FROM schedule WHERE 1=1";

        if (author != null) {
            sql += " AND author = ?";
        }

        if (updatedAt != null) {
            sql += " AND DATE(updatedAt) =?";
        }

        sql += " ORDER BY updatedAt DESC";

        // JdbcTemplate의 query 메서드를 사용하여 SQL을 실행하고, RowMapper를 통해 결과를 매핑하여 반환함.
        return jdbcTemplate.query(sql, new ScheduleRowMapper(), author, updatedAt);
    }

    // 특정 일정 조회 메서드
    //  - 일정 ID를 통해 특정 일정을 조회하고, 해당 일정을 ScheduleEntity 객체로 변환함.
    //  - 파라미터: id(조회할 일정의 고유 ID)
    //  - 동작: SQL 쿼리로 ID 조건에 맞는 레코드를 조회하고, 단일 객체를 반환함.
    //  - 반환값: 조회된 ScheduleEntity 객체
    public ScheduleEntity findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new ScheduleRowMapper(), id);
    }

    // ScheduleRowMapper 내부 클래스:
    //  - ResultSet의 데이터를 ScheduleEntity 객체로 매핑함.
    //  - SQL 쿼리 결과를 ScheduleEntity 객체로 변환함.
    //  - findAll, findById 등에서 쿼리 결과를 ScheduleEntity 객체로 변환할 때 사용됨.
    private static class ScheduleRowMapper implements RowMapper<ScheduleEntity> {

        // @Override 어노테이션:
        //  - 부모 클래스나 인터페이스에 정의된 메서드를 재정의할 때 사용함.
        //  - RowMapper 인터페이스에 정의된 mapRow 메서드를 재정의하고, ResultSet 데이터를 ScheduleEntity 객체로 매핑함.
        @Override

        // mapRow 메서드:
        //  - ResultSet에서 한 행씩 데이터를 읽고 ScheduleEntity 객체로 변환함.
        //  - rs: 현재 행의 데이터를 포함한 ResultSet 객체.
        //  - rowNum: ResultSet에서 현재 행의 순번(0부터 시작함).
        public ScheduleEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

            // 새로운 ScheduleEntity 객체 생성:
            //  - 새로 생성된 ScheduleEntity 객체에 ResultSet의 열 데이터를 각 필드에 설정하여 반환함.
            ScheduleEntity schedule = new ScheduleEntity();

            // ResultSet의 열 데이터를 ScheduleEntity 객체의 필드에 매핑함.
            // 각 필드에 ResultSet의 열 값을 가져와서 설정함.
            schedule.setId(rs.getLong("id"));   // 일정 고유 ID
            schedule.setTask(rs.getString("task")); // 일정 내용
            schedule.setAuthor(rs.getString("author")); // 작성자 이름
            schedule.setAuthor(rs.getString("password")); // 수정/삭제 시 필요한 비밀번호
            schedule.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());  // 생성일
            schedule.setUpdatedAt(rs.getTimestamp("updatedAt").toLocalDateTime());  // 수정일

            // 매핑된 ScheduleEntity 객체 반환:
            //  - mapRow 메서드는 ScheduleEntity 객체를 반환하여 쿼리 결과의 각 행이 ScheduleEntity 객체로 변환되게 함.
            return schedule;
        }
    }
}
