package com.example.schedule;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    // 조건에 따른 쿼리 생성 및 매개변수 설정
    public List<ScheduleEntity> findAll(String author, LocalDateTime updatedAt) {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (author != null) {
            sql.append(" AND author = ?");
            params.add(author);
        }

        if (updatedAt != null) {
            sql.append(" AND DATE(updatedAt) = ?");
            params.add(updatedAt.toLocalDate()); // LocalDateTime의 날짜 부분만 비교
        }

        sql.append(" ORDER BY updatedAt DESC");

        // 동적으로 생성한 매개변수를 전달하여 쿼리 실행
        return jdbcTemplate.query(sql.toString(), params.toArray(), new ScheduleRowMapper());
    }

    // 일정 저장 메서드: ScheduleEntity 객체를 데이터베이스에 삽입
    public int save(ScheduleEntity schedule) {
        String sql = "INSERT INTO schedule (task, author, password, creadtedAt, updatedAt) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                schedule.getTask(),
                schedule.getAuthor(),
                schedule.getPassword(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }

    public ScheduleEntity findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        List<ScheduleEntity> results = jdbcTemplate.query(sql, new Object[]{id}, new ScheduleRowMapper());

        if (results.isEmpty()) {
            return null; // id에 해당하는 결과가 없으면 null 반환
        } else {
            return results.get(0); // 결과가 있을 경우 첫 번째 결과 반환
        }
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

