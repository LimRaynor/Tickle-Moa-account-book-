# Local DB Setup Guide (MariaDB)

이 문서는 `account-book` 프로젝트와 동일한 로컬 DB 환경을 다른 PC에서도 빠르게 재현하기 위한 가이드입니다.

기준 설정값:
- DBMS: `MariaDB 10.11+`
- Host: `localhost`
- Port: `3306`
- DB Name: ``
- DB User: `account`
- DB Password: `book`

백엔드 연결 기준 파일:
- `backend/src/main/resources/application.yml`

## 1) MariaDB 실행 후 접속

```bash
mysql -u root -p
```

## 2) DB/계정 생성

아래 SQL을 그대로 실행하세요.

```sql
CREATE DATABASE IF NOT EXISTS account_book_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'account'@'localhost' IDENTIFIED BY 'book';
GRANT ALL PRIVILEGES ON account_book_db.* TO 'account'@'localhost';
FLUSH PRIVILEGES;
```

## 3) 스키마 생성

```sql
USE account_book_db;

CREATE TABLE IF NOT EXISTS users (
  user_id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(50) NOT NULL DEFAULT 'USER',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id),
  UNIQUE KEY uk_users_email (email)
);

CREATE TABLE IF NOT EXISTS accounts (
  account_id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  name VARCHAR(100) NOT NULL,
  balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (account_id),
  KEY idx_accounts_user_id (user_id),
  CONSTRAINT fk_accounts_user
    FOREIGN KEY (user_id) REFERENCES users(user_id)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS transactions (
  tran_id BIGINT NOT NULL AUTO_INCREMENT,
  account_id BIGINT NOT NULL,
  type VARCHAR(20) NOT NULL,
  category VARCHAR(100) DEFAULT NULL,
  amount DECIMAL(15,2) NOT NULL,
  description VARCHAR(255) DEFAULT NULL,
  date DATE NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (tran_id),
  KEY idx_transactions_account_id (account_id),
  CONSTRAINT fk_transactions_account
    FOREIGN KEY (account_id) REFERENCES accounts(account_id)
    ON DELETE CASCADE
);
```

## 4) 생성 확인

```sql
SHOW DATABASES LIKE 'account_book_db';
USE account_book_db;
SHOW TABLES;
DESCRIBE users;
DESCRIBE accounts;
DESCRIBE transactions;
```

정상이라면 `users`, `accounts`, `transactions` 3개 테이블이 보여야 합니다.

## 5) 백엔드 연결 확인

백엔드 실행:

```bash
cd backend
./gradlew bootRun
```

Windows(cmd/powershell)에서는:

```powershell
cd backend
.\gradlew.bat bootRun
```

실행 후 에러 없이 8080 포트로 뜨면 연결 완료입니다.
