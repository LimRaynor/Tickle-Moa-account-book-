# Model Package Guide

## 역할
- DB 테이블과 매핑되는 도메인 모델(VO) 정의

## 주요 파일
- `User.java` -> `users`
- `Account.java` -> `accounts`
- `Transaction.java` -> `transactions`

## 주의
- 현재 코드에서는 API 입출력에도 model이 일부 직접 사용됩니다.
- 외부 노출을 줄이려면 DTO를 거쳐 전달하는 방식이 더 안전합니다.
