-- 사용자 테이블: user
CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 사용자 고유 ID
                      email VARCHAR(100) NOT NULL UNIQUE,  -- 이메일 (고유 값)
                      name VARCHAR(50) NOT NULL,           -- 사용자 이름
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 생성일자
                      modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 수정일자
);

-- 통화 테이블: currency
CREATE TABLE currency (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,     -- 통화 고유 ID
                          exchange_rate DECIMAL(10, 2) NOT NULL,    -- 환율
                          currency_name VARCHAR(50) NOT NULL,       -- 통화 이름
                          symbol VARCHAR(5) NOT NULL,               -- 통화 기호
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 생성일자
                          modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 수정일자
);