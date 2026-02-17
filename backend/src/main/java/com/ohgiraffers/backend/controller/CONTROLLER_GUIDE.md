# Controller Package Guide

## 역할
- HTTP 요청을 받아 서비스 계층으로 전달하고 응답을 반환합니다.

## 주요 파일
- `AuthController.java`
- `AccountController.java`
- `TransactionController.java`

## 엔드포인트
- Auth: `/api/auth/signup`, `/api/auth/login`
- Account: `/api/accounts`
- Transaction: `/api/transactions`

## 흐름
```text
Client -> Controller -> Service -> (응답)
```
