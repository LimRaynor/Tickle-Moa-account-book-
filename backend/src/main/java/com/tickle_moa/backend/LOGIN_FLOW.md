# POST /api/v1/auth/login 흐름 분석

## 목차
1. [전체 흐름 다이어그램](#1-전체-흐름-다이어그램)
2. [요청 진입 - AuthController](#2-요청-진입---authcontroller)
3. [SecurityConfig - 필터 허용 설정](#3-securityconfig---필터-허용-설정)
4. [AuthService - 사용자 검증](#4-authservice---사용자-검증)
5. [JwtTokenProvider - 토큰 생성](#5-jwttokenprovider---토큰-생성)
6. [RefreshToken DB 저장](#6-refreshtoken-db-저장)
7. [응답 반환](#7-응답-반환)
8. [예외 처리 흐름](#8-예외-처리-흐름)
9. [관련 클래스 구조](#9-관련-클래스-구조)

---

## 1. 전체 흐름 다이어그램

```
[클라이언트]
    │
    │  POST /api/v1/auth/login
    │  Body: { "username": "user1", "password": "1234" }
    │
    ▼
[JwtAuthenticationFilter]           ← 모든 요청에 실행
    │  Authorization 헤더 없음
    │  → 토큰 없으므로 인증 처리 건너뜀
    │  → filterChain.doFilter() 호출로 다음으로 진행
    ▼
[SecurityConfig - authorizeHttpRequests]
    │  /api/v1/auth/login → permitAll()
    │  → 인증 없이 통과
    ▼
[AuthController.login()]
    │  @RequestBody LoginRequest 수신
    │  → authService.login(loginRequest) 호출
    ▼
[AuthService.login()]
    ├── ① userRepository.findByUsername()   → DB 조회
    ├── ② passwordEncoder.matches()         → BCrypt 비밀번호 검증
    ├── ③ jwtTokenProvider.createToken()    → Access Token 생성
    ├── ④ jwtTokenProvider.createRefreshToken() → Refresh Token 생성
    └── ⑤ authRepository.save()            → Refresh Token DB 저장
    ▼
[AuthController.buildTokenResponse()]
    ├── Access Token  → Response Body (JSON)
    └── Refresh Token → HttpOnly Cookie (Set-Cookie 헤더)
    ▼
[클라이언트]
    HTTP 200 OK
    Set-Cookie: refreshToken=eyJ...; HttpOnly; SameSite=Strict; Max-Age=604800; Path=/
    Body: { "success": true, "data": { "accessToken": "eyJ...", "refreshToken": "eyJ..." } }
```

---

## 2. 요청 진입 - AuthController

**파일:** `auth/controller/AuthController.java:23-30`

```java
@PostMapping("/login")
public ResponseEntity<ApiResponse<TokenResponse>> login(
        @RequestBody LoginRequest loginRequest
){
    TokenResponse response = authService.login(loginRequest);
    return buildTokenResponse(response);
}
```

### LoginRequest DTO
**파일:** `auth/dto/LoginRequest.java`

```java
@Getter
@RequiredArgsConstructor
public class LoginRequest {
    private final String username;  // 사용자 아이디
    private final String password;  // 평문 비밀번호
}
```

클라이언트는 다음 형태로 JSON을 전송한다:
```json
{
  "username": "user1",
  "password": "1234"
}
```

---

## 3. SecurityConfig - 필터 허용 설정

**파일:** `config/SecurityConfig.java:43-75`

로그인 엔드포인트는 인증 없이 접근 가능하도록 설정되어 있다.

```java
.authorizeHttpRequests(auth -> auth
    // 회원가입, 로그인, 토큰 재발급은 누구나 허용
    .requestMatchers(
        HttpMethod.POST,
        "/api/v1/users",
        "/api/v1/auth/login",
        "/api/v1/auth/refresh"
    ).permitAll()
    ...
)
```

### 주요 Security 설정

| 설정 항목 | 값 | 이유 |
|-----------|-----|------|
| CSRF | `disable` | JWT는 Stateless → CSRF 보호 불필요 |
| Session | `STATELESS` | 세션을 생성하지 않음 |
| /api/v1/auth/login | `permitAll()` | 비인증 사용자도 로그인 가능해야 함 |

---

## 4. AuthService - 사용자 검증

**파일:** `auth/service/AuthService.java:26-61`

### ① username으로 DB 조회

```java
User user = userRepository.findByUsername(request.getUsername())
    .orElseThrow(() -> new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다"));
```

- `users` 테이블에서 username이 일치하는 행을 조회
- 존재하지 않으면 `BadCredentialsException` 발생
- 보안상 "아이디가 없습니다"가 아닌 "비밀번호가 일치하지 않습니다"와 동일 메시지 사용
  → 아이디 존재 여부 노출 방지

### ② BCrypt 비밀번호 검증

```java
if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
    throw new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다");
}
```

- `matches(평문, 암호화된값)` → BCrypt 내부적으로 salt 포함 비교
- DB에는 평문이 절대 저장되지 않음 (회원가입 시 인코딩하여 저장)

### User 엔티티 구조

**파일:** `command/entity/User.java`

```java
@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;        // 로그인 아이디 (유일)

    @Column(nullable = false)
    private String password;        // BCrypt 암호화된 비밀번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.USER;  // USER | ADMIN
}
```

---

## 5. JwtTokenProvider - 토큰 생성

**파일:** `jwt/JwtTokenProvider.java`

### application.yml JWT 설정

```yaml
jwt:
  secret: /TX6txOQJw4rDYj82onjCb...  # HMAC-SHA 서명 비밀키 (Base64 인코딩)
  expiration: 1800000                  # Access Token 만료: 30분 (ms)
  refresh-expiration: 604800000        # Refresh Token 만료: 7일 (ms)
```

### Access Token 생성

```java
public String createToken(String username, String role){
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtExpiration); // 30분 후

    return Jwts.builder()
        .subject(username)        // Payload: sub (사용자 식별자)
        .claim("role", role)      // Payload: role (권한 정보)
        .issuedAt(now)            // Payload: iat (발행 시각)
        .expiration(expiryDate)   // Payload: exp (만료 시각)
        .signWith(secretKey)      // Signature: HMAC-SHA 서명
        .compact();               // "Header.Payload.Signature" 문자열 반환
}
```

### Refresh Token 생성

```java
public String createRefreshToken(String username, String role){
    Date expiryDate = new Date(now.getTime() + jwtRefreshExpiration); // 7일 후
    // createToken과 구조 동일, 만료 시간만 다름
    ...
}
```

### JWT 구조

```
eyJhbGciOiJIUzI1NiJ9  .  eyJzdWIiOiJ1c2VyMSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNzAwMDAwMDAwLCJleHAiOjE3MDAwMDE4MDB9  .  SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
      Header                                        Payload                                                                                                                              Signature

Header:  { "alg": "HS256" }
Payload: { "sub": "user1", "role": "USER", "iat": 1700000000, "exp": 1700001800 }
Signature: HMAC-SHA256(base64(header) + "." + base64(payload), secretKey)
```

| 항목 | Access Token | Refresh Token |
|------|-------------|---------------|
| 만료 시간 | 30분 | 7일 |
| 용도 | API 인증 | Access Token 재발급 요청 |
| 전달 방식 | Authorization 헤더 | HttpOnly Cookie |
| DB 저장 | X | O |

---

## 6. RefreshToken DB 저장

**파일:** `auth/service/AuthService.java:45-54`

```java
RefreshToken tokenEntity = RefreshToken.builder()
    .username(user.getUsername())   // PK (username)
    .token(refreshToken)            // 토큰 문자열
    .expiryDate(
        new Date(System.currentTimeMillis() + jwtTokenProvider.getRefreshExpiration())
    ).build();

authRepository.save(tokenEntity);
```

### RefreshToken 엔티티

**파일:** `auth/entity/RefreshToken.java`

```java
@Entity
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    private String username;    // PK (1사용자 = 1 Refresh Token)

    @Column(nullable = false)
    private String token;       // 토큰 문자열

    @Column(nullable = false)
    private Date expiryDate;    // 만료 일시
}
```

**DB 저장 이유:**
- `/api/v1/auth/refresh` 요청 시, 클라이언트가 보낸 토큰과 DB 값을 대조
- 토큰이 탈취되었을 경우 DB에서 삭제하여 무효화 가능
- `username`이 PK이므로 로그인 시 기존 토큰이 자동으로 덮어씌워짐 (단일 세션 관리)

---

## 7. 응답 반환

**파일:** `auth/controller/AuthController.java:75-92`

### buildTokenResponse() - 응답 조립

```java
private ResponseEntity<ApiResponse<TokenResponse>> buildTokenResponse(TokenResponse tokenResponse) {
    ResponseCookie cookie = createRefreshTokenCookie(tokenResponse.getRefreshToken());
    return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())  // 쿠키 설정
            .body(ApiResponse.success(tokenResponse));          // JSON body
}
```

### Refresh Token 쿠키 설정

```java
ResponseCookie.from("refreshToken", refreshToken)
    .httpOnly(true)             // JavaScript에서 접근 불가 → XSS 방어
    // .secure(true)            // HTTPS 환경에서만 전송 (운영 시 활성화)
    .path("/")                  // 모든 경로에서 쿠키 전송
    .maxAge(Duration.ofDays(7)) // 브라우저 쿠키 유효기간: 7일
    .sameSite("Strict")         // 동일 사이트 요청만 쿠키 전송 → CSRF 방어
    .build();
```

### 최종 HTTP 응답

```http
HTTP/1.1 200 OK
Content-Type: application/json
Set-Cookie: refreshToken=eyJ...; HttpOnly; SameSite=Strict; Max-Age=604800; Path=/

{
  "success": true,
  "data": {
    "accessToken":  "eyJhbGciOiJIUzI1NiJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
  },
  "timestamp": "2024-11-15T10:30:00"
}
```

> **참고:** Refresh Token은 Response Body와 Cookie 양쪽에 모두 담겨 반환된다.
> 클라이언트는 Cookie의 Refresh Token을 사용하고 (자동 전송),
> Body의 Refresh Token은 무시하거나 안전하게 폐기하는 것이 권장된다.

---

## 8. 예외 처리 흐름

| 상황 | 발생 위치 | 예외 | 결과 |
|------|-----------|------|------|
| username 없음 | `AuthService:31` | `BadCredentialsException` | 401 |
| 비밀번호 불일치 | `AuthService:35` | `BadCredentialsException` | 401 |
| 토큰 서명 오류 | `JwtTokenProvider:77` | `BadCredentialsException` | 401 |
| 토큰 만료 | `JwtTokenProvider:79` | `BadCredentialsException` | 401 |

---

## 9. 관련 클래스 구조

```
POST /api/v1/auth/login 관련 클래스

auth/
├── controller/
│   └── AuthController.java         ← 요청 진입점, 응답 조립
├── dto/
│   ├── LoginRequest.java           ← 요청 DTO (username, password)
│   └── TokenResponse.java          ← 응답 DTO (accessToken, refreshToken)
├── entity/
│   └── RefreshToken.java           ← DB 저장 엔티티
├── repository/
│   └── AuthRepository.java         ← refresh_token 테이블 접근
└── service/
    ├── AuthService.java             ← 로그인 핵심 비즈니스 로직
    └── CustomUserDetailsService.java ← JwtAuthenticationFilter에서 사용

command/
├── entity/
│   ├── User.java                   ← users 테이블 엔티티
│   └── UserRole.java               ← USER | ADMIN 열거형
└── repository/
    └── UserRepository.java         ← users 테이블 접근

jwt/
├── JwtTokenProvider.java           ← 토큰 생성/검증
└── JwtAuthenticationFilter.java    ← 매 요청마다 토큰 검사

config/
└── SecurityConfig.java             ← Security 설정, 필터 체인 구성

common/
└── ApiResponse.java                ← 공통 응답 래퍼
```

---

## 핵심 설계 포인트 요약

| 포인트 | 내용 |
|--------|------|
| **Stateless** | 세션 없이 토큰으로만 인증 상태 유지 |
| **BCrypt** | 비밀번호는 단방향 해시로 저장, 평문 절대 저장 안 함 |
| **이중 토큰** | 짧은 수명 Access Token + 긴 수명 Refresh Token |
| **HttpOnly Cookie** | Refresh Token을 JS 접근 불가 쿠키로 전달 → XSS 방어 |
| **SameSite=Strict** | 타 사이트 요청 시 쿠키 미전송 → CSRF 방어 |
| **DB 저장** | Refresh Token만 DB 저장 → 토큰 탈취 시 무효화 가능 |
| **에러 동일화** | 아이디 없음/비밀번호 틀림 모두 동일 메시지 → 정보 노출 방지 |
