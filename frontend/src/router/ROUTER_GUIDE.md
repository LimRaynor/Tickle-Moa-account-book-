# Router Package Guide

## 역할
- 페이지 라우팅 설정

## 주요 파일
- `index.js`

## 현재 라우트
- `/` -> `Main.vue`
- `/about` -> `AboutView.vue`
- `/signup` -> `Signup.vue`
- `/login` -> `LoginView.vue`
- `/accounts` -> `AccountView.vue`
- `/accounts/create` -> `AccountView.vue`
- `/transactions` -> `TransactionView.vue`

## 흐름
```text
사용자 URL 접근 -> Router 매칭 -> 해당 View 렌더링
```
