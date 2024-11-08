package com.example.schedule;


//3. ScheduleRepository 클래스
//        -> JdbcTemplate을 활용하여 자바에서 CRUD를 구현.

//        Create: 일정 생성 -> INSERT 쿼리를 사용하여 데이터베이스에 데이터를 추가.
//        Read: 전체 일정 조회 및 선택 일정 조회 -> SELECT 쿼리를 사용. 이때, RowMapper를 이용하여 데이터베이스의 데이터를 각 클래스의 객체로 Mapping한다.
//        Update: 선택 일정 수정 -> UPDATE 쿼리를 사용하여 id와 password를 확인 후, 데이터베이스에서 데이터를 수정함.
//        Delete: 선택 일정 삭제 -> DELETE 쿼리를 사용하여 id와 password를 확인 후, 데이터베이스에서 데이터를 삭제함.

public class ScheduleRepository {
    
}
