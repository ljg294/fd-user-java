DROP TABLE IF EXISTS member;

#-----------------------------------------------------------------------------------------------------------------------
#-- Table 명 : member (회원)
#-----------------------------------------------------------------------------------------------------------------------
CREATE TABLE member
(
    member_id                        BIGINT                      NOT NULL         AUTO_INCREMENT  COMMENT '회원ID',
    member_type                      VARCHAR(10)                 NOT NULL                         COMMENT '회원 유형',
    email                            VARCHAR(255)                NOT NULL         UNIQUE          COMMENT '이메일',
    password                         VARCHAR(255)                NOT NULL                         COMMENT '비밀번호',
    name                             VARCHAR(100)                NOT NULL                         COMMENT '이름',
    age                              INT                         NULL                             COMMENT '나이',
    gender                           VARCHAR(6)                  NULL                             COMMENT '성별',
    mobile                           VARCHAR(20)                 NULL             UNIQUE          COMMENT '휴대폰번호',
    signup_date                      DATE                        NOT NULL                         COMMENT '가입일자',
    delete_yn                        varchar(1)                  NOT NULL                         COMMENT '삭제여부',
    created_member_id                BIGINT                      NOT NULL                         COMMENT '등록자ID',
    created_date_time                DATETIME                    NOT NULL                         COMMENT '등록일시',
    modified_member_id               BIGINT                      NOT NULL                         COMMENT '수정자ID',
    modified_date_time               DATETIME                    NOT NULL                         COMMENT '수정일시',

    CONSTRAINT pk_member PRIMARY KEY (member_id)
    CONSTRAINT chk_member_member_type CHECK (member_type IN ('CUSTOMER', 'CHEF')),
    CONSTRAINT chk_member_email CHECK (email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
    CONSTRAINT chk_member_name CHECK (LENGTH(name) BETWEEN 2 AND 100 AND TRIM(name) <> ''),
    CONSTRAINT chk_member_gender CHECK (gender IN ('MALE', 'FEMALE')),
    CONSTRAINT chk_member_delete_yn CHECK (delete_yn IN ('Y', 'N')),
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4 COMMENT='회원';

#-----------------------------------------------------------------------------------------------------------------------
#-- Table 명 : diner (음식점)
#-----------------------------------------------------------------------------------------------------------------------
CREATE TABLE diner
(
    diner_id                         BIGINT                      NOT NULL         AUTO_INCREMENT  COMMENT '음식점 ID',
    owner_member_id                  BIGINT                      NOT NULL                         COMMENT '음식점 보유자 ID',
    diner_name                       VARCHAR(255)                NOT NULL                         COMMENT '음식점 이름',
    diner_address                    VARCHAR(255)                NOT NULL                         COMMENT '음식점 주소',
    delete_yn                        VARCHAR(1)                  NOT NULL                         COMMENT '삭제 여부',
    created_member_id                BIGINT                      NOT NULL                         COMMENT '등록자 ID',
    created_date_time                DATETIME                    NOT NULL                         COMMENT '등록 일시',
    modified_member_id               BIGINT                      NOT NULL                         COMMENT '수정자 ID',
    modified_date_time               DATETIME                    NOT NULL                         COMMENT '수정 일시',

    CONSTRAINT pk_diner PRIMARY KEY (diner_id),
    CONSTRAINT fk_diner_member FOREIGN KEY (owner_member_id) REFERENCES `member`(member_id),
    CONSTRAINT chk_diner_delete_yn CHECK (delete_yn IN ('Y', 'N')),
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4 COMMENT='음식점';

#-----------------------------------------------------------------------------------------------------------------------
#-- Table 명 : diner_schedule (음식점 스케줄)
#-----------------------------------------------------------------------------------------------------------------------
CREATE TABLE `diner_schedule`
(
    diner_schedule_id                BIGINT                      NOT NULL         AUTO_INCREMENT  COMMENT '음식점 스케줄 ID',
    diner_id                         BIGINT                      NOT NULL                         COMMENT '음식점 ID',
    day_of_week                      VARCHAR(3)                  NOT NULL                         COMMENT '요일',
    open_time                        TIME                        NOT NULL                         COMMENT '오픈 시간',
    close_time                       TIME                        NOT NULL                         COMMENT '종료 시간',
    delete_yn                        VARCHAR(1)                  NOT NULL         DEFAULT 'N'     COMMENT '삭제 여부',
    created_user_id                  BIGINT                      NOT NULL                         COMMENT '등록자 ID',
    created_date_time                DATETIME                    NOT NULL                         COMMENT '등록 일시',
    modified_user_id                 BIGINT                      NOT NULL                         COMMENT '수정자 ID',
    modified_date_time               DATETIME                    NOT NULL                         COMMENT '수정 일시',

    PRIMARY KEY (diner_schedule_id),
    CONSTRAINT fk_diner_schedule_diner FOREIGN KEY (diner_id) REFERENCES `diner`(diner_id),
    CONSTRAINT chk_diner_day_of_week CHECK (day_of_week IN ('MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN')),
    CONSTRAINT chk_diner_schedule_delete_yn CHECK (delete_yn IN ('Y', 'N')),
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4 COMMENT='음식점 스케줄';