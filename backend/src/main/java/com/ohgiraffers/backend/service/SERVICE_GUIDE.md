# Service Package Guide

## 역할
- 비즈니스 로직 처리
- 데이터 유효성/중복 확인
- Mapper 호출 조합

## 주요 파일
- `AuthService.java`: 회원가입(중복 이메일 검사, 비밀번호 암호화), 사용자 조회
- `AccountService.java`: 계좌 조회/생성/삭제
- `TransactionService.java`: 거래 조회/생성/삭제

## 흐름
```text
Controller -> Service -> Mapper
```
