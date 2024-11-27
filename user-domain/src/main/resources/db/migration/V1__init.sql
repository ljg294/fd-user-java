DROP TABLE IF EXISTS member;

#-----------------------------------------------------------------------------------------------------------------------
#-- Table 명 : member (회원)
#-----------------------------------------------------------------------------------------------------------------------
CREATE TABLE member
(
    member_id                        BIGINT                      NOT NULL         AUTO_INCREMENT  COMMENT '회원ID',
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
    CONSTRAINT chk_member_email CHECK (email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
    CONSTRAINT chk_member_name CHECK (LENGTH(name) BETWEEN 2 AND 100 AND TRIM(name) <> ''),
    CONSTRAINT chk_member_gender CHECK (gender IN ('MALE', 'FEMALE')),
    CONSTRAINT chk_member_delete_yn CHECK (delete_yn IN ('Y', 'N')),
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4 COMMENT='회원';