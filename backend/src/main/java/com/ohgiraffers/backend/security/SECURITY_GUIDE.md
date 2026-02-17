# Security Package Guide

## 역할
- JWT 관련 인증 컴포넌트 위치

## 현재 파일
- `CustomUserDetailsService.java`
- `JwtAuthenticationFilter.java`
- `JwtTokenProvider.java`

## 현재 상태
- 보안 클래스 골격은 존재하지만, 실제 인증 흐름은 아직 최소 수준입니다.
- `SecurityConfig`가 대부분 요청을 허용하고 있어 완전한 JWT 강제 상태는 아닙니다.

## 권장 흐름(목표)
```text
Login 성공 -> JWT 발급 -> 요청마다 JWT 검증 필터 통과 -> Controller 진입
```
