MariaDB 설정 요약

1. 데이터베이스 생성

CREATE DATABASE IF NOT EXISTS account_book_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

2. 사용자 생성 및 권한

CREATE USER IF NOT EXISTS 'account'@'localhost' IDENTIFIED BY 'book';
GRANT ALL PRIVILEGES ON account_book_db.* TO 'account'@'localhost';
FLUSH PRIVILEGES;

3. 테이블 (이건 네가 직접 넣었으니 네 SQL 그대로)

CREATE TABLE `users` (
`user_id`    BIGINT    NOT NULL AUTO_INCREMENT,
`name`       VARCHAR(255)    NULL,
`email`      VARCHAR(255)    NOT NULL,
`password`   VARCHAR(255)    NOT NULL,
`created_at` DATETIME        NULL,
`role`       VARCHAR(20)     NULL,
PRIMARY KEY (user_id)
);

CREATE TABLE `accounts` (
`account_id` BIGINT    NOT NULL AUTO_INCREMENT,
`user_id`    BIGINT    NULL,
`name`       VARCHAR(255)    NOT NULL,
`balance`    DECIMAL(19, 2)  NOT NULL,
`created_at` DATETIME        NULL,
PRIMARY KEY (account_id)
);

CREATE TABLE `transactions` (
`tran_id`     BIGINT    NOT NULL AUTO_INCREMENT,
`account_id`  BIGINT    NULL,
`type`        VARCHAR(20)     NOT NULL,
`category`    ENUM('deposit', 'withdrawal') NOT NULL,
`amount`      DECIMAL(19, 2)  NOT NULL,
`description` VARCHAR(255)    NULL,
`date`        DATE            NOT NULL,
`created_at`  DATETIME        NULL,
PRIMARY KEY (tran_id)
);

CREATE TABLE `untitled` (
`user_id`       BIGINT    NOT NULL,
`refresh_token` VARCHAR(255)    NULL,
PRIMARY KEY (user_id)
);

4. 접속 정보

┌───────────────┬─────────────────┐
│     항목      │       값        │
├───────────────┼─────────────────┤
│ 호스트        │ localhost       │
├───────────────┼─────────────────┤
│ 포트          │ 3306            │
├───────────────┼─────────────────┤
│ DB명          │ account_book_db │
├───────────────┼─────────────────┤
│ 계정          │ account         │
├───────────────┼─────────────────┤
│ 비밀번호      │ book            │
├───────────────┼─────────────────┤
│ root 비밀번호 │ mariadb         │
└───────────────┴─────────────────┘