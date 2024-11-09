package com.example.schedule;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

// Repository 어노테이션을 사용함으로써 스프링 빈(객체 관리와 주입을 자동화)으로 등록, 데이터 접근 계층 표시, 예외 변환(데이터 베이스와 관련된 예외들을 데이터 접근 예외로 변환)을 한다.
@Repository

//3. ScheduleRepository 클래스
//        -> JdbcTemplate을 활용하여 자바에서 CRUD를 구현.

//              Create: 일정 생성 -> INSERT 쿼리를 사용하여 데이터베이스에 데이터를 추가.
//              Read: 전체 일정 조회 및 선택 일정 조회 -> SELECT 쿼리를 사용. 이때, RowMapper를 이용하여 데이터베이스의 데이터를 각 클래스의 객체로 Mapping한다.
//              Update: 선택 일정 수정 -> UPDATE 쿼리를 사용하여 id와 password를 확인 후, 데이터베이스에서 데이터를 수정함.
//              Delete: 선택 일정 삭제 -> DELETE 쿼리를 사용하여 id와 password를 확인 후, 데이터베이스에서 데이터를 삭제함.
public class ScheduleRepository(JdbcTemplate jdbcTemplate) {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // 일정 저장 메서드
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

    // 전체 일정 조회 메서드
    public List<ScheduleEntity> findAll(String author, LocalDateTime updatedAt) {
        String sql = "SELECT * FROM schedule WHERE 1=1";

        if (author != null) {
            sql += "AND author = ?";
        }

        if (updatedAt != null) {
            sql += "AND DATE(updatedAt) =?";
        }

        sql += " ORDER BY updatedAt DESC";

        return jdbcTemplate.query(sql, new ScheduleRowMapper(), author, updatedAt);
    }

    // 특정 일정 조회 메서드
    public ScheduleEntity findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new ScheduleRowMapper(), id);
    }

    // RowMapper를 통해 데이터베이스의 데이터를 ScheduleEntity 객체로 매핑
    private static class ScheduelRowMapper implements RowMapper<ScheduleEntity> {
        @Override
        public ScheduleEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            ScheduleEntity schedule = new ScheduleEntity();
            schedule.setId(rs.getLong("id"));
            schedule.setTask(rs.getString("tast"));
            schedule.setAuthor(rs.getString("author"));
            schedule.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
            schedule.setUpdatedAt(rs.getTimestamp("updatedAt").toLocalDateTime());
            return schedule;
        }
    }
}
