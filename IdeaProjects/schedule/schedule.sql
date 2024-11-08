-- 1. 테이블 생성 쿼리 (Creat)
CREATE TABLE schedule
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,                              -- 고유 ID (BIGINT 타입)
    task       VARCHAR(255) NOT NULL,                                          -- 할 일 내용
    author     VARCHAR(100) NOT NULL,                                          -- 작성자
    password   VARCHAR(100) NOT NULL,                                          -- 비밀번호(수정/삭제할 때 필요)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                            -- 생성일
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 수정일
);

--2. 일정 생성 쿼리 (Insert)
-- 예시 데이터 삽입
INSERT INTO schedule (task, author, password)
VALUES
    ('TODO 작성', '김씨', '1234'),
    ('TIL', '찰스', 'qwer1234'),
    ('일정관리앱 개발', 'dev', 'abcdefg');

--3. 전체 일정 조회 쿼리 (Select)
SELECT id, task, author, created_at, updated_at
FROM schedule
ORDER BY updated_at DESC;

--4. 선택 일정 조회 쿼리 (Select)
-- 선택 id의 일정을 조회할 때
SELECT id, task, author, created_at, updated_at
FROM schedule
WHERE id = 1;   -- 조회할 일정의 ID


--5. 선택 일정 수정 쿼리 (Update)
-- 선택 id의 일정을 수정할 때
-- 비밀번호가 일치할 경우에만 수정 가능
UPDATE schedule
SET task = '수정된 할 일 내용',
    author = '수정된 작성자 이름',
    updated_at = CURRENT_TIMESTAMP
WHERE id = 1
  AND password = '1234';    -- 수정할 일정의 ID와 비밀번호


--6. 선택 일정 삭제 쿼리 (Delete)
-- 선택 id의 일정을 삭제할 때
-- 비밀번호가 일치할 경우에만 삭제 가능
DELETE FROM schedule
WHERE id = 1
  AND password = '1234';    -- 삭제할 일정의 ID와 비밀번호