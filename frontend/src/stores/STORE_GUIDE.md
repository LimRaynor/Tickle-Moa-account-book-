# Store Package Guide

## 역할
- Pinia 기반 상태 관리

## 주요 파일
- `auth.js`: 로그인 사용자 상태
- `account.js`: 계좌 목록/추가/삭제 + API 호출
- `transaction.js`: 거래 목록/추가/삭제 + API 호출

## 흐름
```text
View 이벤트 -> Store action -> API 호출 -> Store state 갱신 -> UI 반영
```
