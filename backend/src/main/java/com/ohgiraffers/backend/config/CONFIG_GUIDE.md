# Config Package Guide

## 역할
- Spring Security/CORS 등 서버 공통 설정을 관리합니다.

## 주요 파일
- `SecurityConfig.java`: 인증/인가 규칙, `BCryptPasswordEncoder` 빈 등록
- `WebConfig.java`: CORS 허용 정책 설정

## 현재 동작
- `/api/auth/**`는 인증 없이 접근 허용
- 그 외 요청도 현재는 `permitAll()` 상태
- 프론트(`http://localhost:5173`)에서 `/api/**` 호출 허용

## 요청 흐름에서 위치
```text
HTTP 요청 -> Security Filter Chain -> Controller
```
