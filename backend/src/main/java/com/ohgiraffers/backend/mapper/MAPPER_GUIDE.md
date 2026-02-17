# Mapper Package Guide

## 역할
- MyBatis Mapper 인터페이스
- XML SQL과 Java 메서드를 연결

## 주요 파일
- `UserMapper.java` <-> `resources/mappers/UserMapper.xml`
- `AccountMapper.java` <-> `resources/mappers/AccountMapper.xml`
- `TransactionMapper.java` <-> `resources/mappers/TransactionMapper.xml`

## 흐름
```text
Service 호출 -> Mapper 인터페이스 -> Mapper XML SQL 실행
```
