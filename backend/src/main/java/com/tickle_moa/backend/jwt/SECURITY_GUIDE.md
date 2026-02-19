# SECURITY GUIDE (JWT 적용 전)

## 이 패키지의 역할
- JWT 인증/인가 관련 클래스 위치

## 파일별 해석

### `CustomUserDetailsService.java`
- 현재: 빈 클래스
- 예정 역할: 사용자 조회 후 Spring Security 인증 객체 생성

### `JwtAuthenticationFilter.java`
- 현재: 빈 클래스
- 예정 역할: 요청 헤더의 JWT를 파싱/검증하고 SecurityContext 설정

### `JwtTokenProvider.java`
- 현재: 빈 클래스
- 예정 역할: 토큰 발급/검증/클레임 추출

## 현재 상태 요약
- Security 패키지는 뼈대만 있고 실제 JWT 로직 미구현
- 따라서 현재 인증은 "JWT 기반 보호" 단계가 아님
