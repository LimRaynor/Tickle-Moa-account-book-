# Resources Guide

## 역할
- 애플리케이션 환경설정 및 SQL 매퍼 저장

## 주요 파일
- `application.yml`: DB 연결, MyBatis 경로, 서버 포트
- `mappers/UserMapper.xml`
- `mappers/AccountMapper.xml`
- `mappers/TransactionMapper.xml`

## 핵심 설정
- datasource: `jdbc:mariadb://localhost:3306/account_book_db`
- mybatis mapper-locations: `classpath:mappers/**/*.xml`
- server port: `8080`
