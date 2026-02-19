9# Tickle-Moa Account Book

개인 가계부 웹 프로젝트입니다. 백엔드는 Spring Boot + MyBatis, 프론트엔드는 Vue 3 + Pinia + Axios로 구성되어 있습니다.

## 기술 스택

- Backend: Spring Boot 3.5.10, Spring Security, MyBatis
- Frontend: Vue 3, Vite, Pinia, Axios
- Database: MariaDB (account_book_db)
- Language: Java 21, JavaScript
- Build: Gradle, npm

## 프로젝트 구조

```
account-book/
├─ backend/
│  ├─ src/main/java/com/tickle_moa/backend/
│  │  ├─ auth/
│  │  │  ├─ controller/    ← 인증 API (로그인/회원가입)
│  │  │  ├─ dto/           ← 인증 요청/응답 객체
│  │  │  └─ service/       ← 인증 비즈니스 로직
│  │  ├─ config/           ← 보안/CORS 설정
│  │  ├─ controller/       ← 계좌/거래 API
│  │  ├─ jwt/              ← JWT 토큰 생성/검증/필터
│  │  ├─ mapper/           ← MyBatis 인터페이스
│  │  ├─ model/            ← DB 테이블 매핑 객체
│  │  └─ service/          ← 계좌/거래 비즈니스 로직
│  └─ src/main/resources/
│     ├─ application.yml
│     └─ mappers/
├─ frontend/
│  └─ src/
│     ├─ api/
│     ├─ assets/
│     ├─ router/
│     ├─ stores/
│     └─ views/
├─ test/
├─ LOCAL_DB_SETUP.md
├─ ARCHITECTURE.md
├─ LIBRARY_TRACKER.md
└─ README.md
```

## 백엔드 역할 정리

- **auth/controller**: 인증 API 입구 (로그인, 회원가입)
- **auth/dto**: 인증 요청/응답 전용 객체 (LoginRequest, SignupRequest, TokenResponse)
- **auth/service**: 인증 비즈니스 로직 (AuthService, CustomUserDetailsService)
- **jwt**: JWT 토큰 생성/검증/필터 (JwtTokenProvider, JwtAuthenticationFilter)
- **config**: 보안/CORS/공통 설정 담당 (SecurityConfig, WebConfig)
- **controller**: 계좌/거래 API 입구 (AccountController, TransactionController)
- **service**: 계좌/거래 비즈니스 로직 담당 (AccountService, TransactionService)
- **mapper**: Service 요청을 받아 MyBatis XML SQL 실행
- **model**: DB 테이블과 매핑되는 도메인 객체(VO/Entity 성격)
- **MariaDB**: 데이터 저장/조회/수정/삭제 수행

## 실행 방법

```bash
# backend
cd backend
./gradlew bootRun

# frontend
cd frontend
npm install
npm run dev
```

Windows PowerShell에서는 backend 실행 시:

```bash
cd backend
.\gradlew.bat bootRun
```

## DB 정보

- Host: localhost:3306
- Database: account_book_db
- User/Password: account / book
- 상세 세팅: LOCAL_DB_SETUP.md

## API 요약

```
POST /api/auth/signup
POST /api/auth/login
GET  /api/accounts?userId={userId}
POST /api/accounts
DELETE /api/accounts/{id}
GET  /api/transactions?accountId={accountId}
POST /api/transactions
DELETE /api/transactions/{id}
```

## 요청 흐름

```
Vue View -> Pinia Store -> Axios(api/axios.js)
        -> Spring Controller -> Service -> Mapper(XML) -> MariaDB
```

## 패키지별 가이드

**Backend**
- backend/src/main/java/com/tickle_moa/backend/auth/controller/AUTH_CONTROLLER_GUIDE.md
- backend/src/main/java/com/tickle_moa/backend/auth/dto/DTO_GUIDE.md
- backend/src/main/java/com/tickle_moa/backend/auth/service/AUTH_SERVICE_GUIDE.md
- backend/src/main/java/com/tickle_moa/backend/jwt/JWT_GUIDE.md
- backend/src/main/java/com/tickle_moa/backend/config/CONFIG_GUIDE.md
- backend/src/main/java/com/tickle_moa/backend/controller/CONTROLLER_GUIDE.md
- backend/src/main/java/com/tickle_moa/backend/service/SERVICE_GUIDE.md
- backend/src/main/java/com/tickle_moa/backend/mapper/MAPPER_GUIDE.md
- backend/src/main/java/com/tickle_moa/backend/model/MODEL_GUIDE.md
- backend/src/main/resources/RESOURCES_GUIDE.md

**Frontend**
- frontend/src/api/API_GUIDE.md
- frontend/src/router/ROUTER_GUIDE.md
- frontend/src/stores/STORE_GUIDE.md
- frontend/src/views/VIEWS_GUIDE.md
- frontend/src/assets/ASSETS_GUIDE.md

## 테스트 진행 순서

1. 회원가입
2. 로그인
3. 계좌 등록
4. 거래내역 / 거래내역 등록
5. DB 등록 확인

## 버그 발견현황

**1) 로그인 전 페이지 이동 불가**

- 증상: 로그인하기 전 계좌, 거래내역 페이지 선택 후 다른 페이지로 이동이 안됨 (URL은 바뀌는데 화면은 그대로)

**2) BOM 문자로 컴파일 실패**

| 항목 | 내용 |
|---|---|
| 증상 | `illegal character: '\ufeff'` 에러로 gradlew bootRun 실패 |
| 원인 | AccountController.java, TransactionController.java, AuthController.java 파일 앞에 UTF-8 BOM(EF BB BF) 3바이트가 붙어있었음. Windows 메모장 등 일부 에디터가 자동 삽입 |
| 해결 | sed로 3개 파일의 BOM 바이트 제거 |

**3) AuthController 한글 인코딩 깨짐**

| 항목 | 내용 |
|---|---|
| 증상 | `ResponseEntity.ok("?뚯썝媛???깃났")` — 한글이 깨진 상태로 저장됨 |
| 원인 | UTF-8 파일을 다른 인코딩(EUC-KR 등)으로 다시 열어서 저장하면서 한글 바이트가 변환됨 |
| 해결 | 파일을 정상 UTF-8로 재작성, "회원가입 성공"으로 복원 |

**4) Spring Security 401 Unauthorized**

| 항목 | 내용 |
|---|---|
| 증상 | 서버는 켜지지만 모든 API 요청에 401 Unauthorized 응답 |
| 원인 | build.gradle에 spring-boot-starter-security 의존성이 있는데 SecurityConfig.java가 비어있었음. (1) BCryptPasswordEncoder 빈 미등록 → 서버 시작 실패, (2) SecurityFilterChain 미설정 → 모든 요청 차단 |
| 해결 | SecurityConfig에 BCryptPasswordEncoder 빈 등록 + permitAll() 필터체인 추가 (JWT 미구현이라 전체 허용) |

## 변경 이력

### 2026-02-19 — 패키지 구조 변경 (수업자료 방식)

**배경**: 인증 관련 파일이 controller/, dto/, service/ 등에 흩어져 있어 수업자료처럼 auth/ 패키지로 통합 정리.

| 변경 전 | 변경 후 |
|---|---|
| `controller/AuthController` | `auth/controller/AuthController` |
| `dto/LoginRequest` | `auth/dto/LoginRequest` |
| `dto/SignupRequest` | `auth/dto/SignupRequest` |
| `dto/TokenResponse` | `auth/dto/TokenResponse` |
| `service/AuthService` | `auth/service/AuthService` |
| `security/CustomUserDetailsService` | `auth/service/CustomUserDetailsService` |
| `security/JwtTokenProvider` | `jwt/JwtTokenProvider` |
| `security/JwtAuthenticationFilter` | `jwt/JwtAuthenticationFilter` |

### 2026-02-18 — JWT 인증 구현

**배경**: 기존 로그인은 이메일 조회만 하고 비밀번호 검증 없이 User 객체를 그대로 반환했으며, 모든 API 엔드포인트가 인증 없이 열려 있었음.



## test 
1 - 회원가입
<img width="822" height="776" alt="1 회원가입test" src="https://github.com/user-attachments/assets/5c41d3c0-518a-445d-88ef-08dc0382251c" />

2 - 로그인

<img width="854" height="766" alt="2  로그인test" src="https://github.com/user-attachments/assets/f7c9e763-f0f9-4dba-becf-0c874df11000" />

3- 계좌등록

<img width="912" height="343" alt="3 계좌등록test" src="https://github.com/user-attachments/assets/711f6d34-dcaf-4e21-b276-c77805853721" />


4 - 거래내역 

<img width="849" height="298" alt="4 거래내역test" src="https://github.com/user-attachments/assets/aa180a52-82cb-43b2-92f6-b1c1210e740b" />


4.1 - 거래내역등록

<img width="1258" height="335" alt="4_1 거래내역등록test" src="https://github.com/user-attachments/assets/cfef0a0c-6c60-4939-9eef-9296f498b6b5" />

5 - DB등록 확인

<img width="1306" height="987" alt="5 DB등록확인test" src="https://github.com/user-attachments/assets/286100ea-3fc2-4582-bd07-b178569e6a5b" />

# 발견중인 버그
- 로그인 하기전 페이지이동시 계좌, 거래내역 두 페이지를 가게되면 다른페이지로 이동하지못하는 버그 발견

- 계좌등록후 지출, 입금 내역 확인시 지출, 입금 둘다 INCOME으로 출력되는 버그 발견 

- 회원가입후 페이지 이동이없는 버그 발경

- 프론트엔드 꾸미기 이후 계좌추가안되는 버그 발견
- 
# 추가예정인것들
- 로그아웃 버튼 생성

- 회원가입 완료 메세지 이후 로그인 페이지로 이동하게 변경

- 로그인후 기존 로그인창 제거후 상단에 로그인 정보란 추가  (로그아웃버튼옆에)

- 내정보페이지 있으면 좋을거같음

**프론트엔드 변경**

| 파일 | 내용 |
|---|---|
| `api/axios.js` | 요청 인터셉터 활성화 — `localStorage`에서 토큰 읽어 `Authorization: Bearer` 헤더 자동 첨부 |
| `stores/auth.js` | `login()` / `logout()` 추가 — `localStorage` 토큰 저장/삭제 연동 |
| `views/LoginView.vue` | 로그인 성공 시 `authStore.login(res.data)` 호출로 토큰 및 유저 정보 저장 |

**백엔드 변경**

| 파일 | 내용 |
|---|---|
| `application.yml` | `jwt.secret`, `jwt.expiration` 설정값 추가 |
| `auth/dto/LoginRequest` | 로그인 요청 DTO (email, password) |
| `auth/dto/SignupRequest` | 회원가입 요청 DTO (name, email, password) |
| `auth/dto/TokenResponse` | 로그인 응답 DTO (token, userId, name, email, role) |
| `jwt/JwtTokenProvider` | JWT 생성 / 검증 / 파싱 컴포넌트 |
| `auth/service/CustomUserDetailsService` | 이메일 기반 UserDetails 조회 |
| `jwt/JwtAuthenticationFilter` | Authorization 헤더에서 토큰 추출 후 SecurityContext 등록 |
| `config/SecurityConfig` | JWT 필터 등록, `/api/auth/**` 공개, 나머지 인증 필요, Stateless 세션 |
| `auth/service/AuthService` | `login()` 추가 — BCrypt 비밀번호 검증 포함 |
| `auth/controller/AuthController` | 로그인 응답을 `TokenResponse`로 변경 (JWT 토큰 포함) |


**인증 흐름**

```
[클라이언트(브라우저/모바일앱)]             [서버(Spring Boot)]                     [데이터베이스]
         |                                       |                                     |
         | --- 1. 로그인 요청 (ID/PW) ---------> |                                     |
         |                                       | --- 2. 회원 정보 조회/검증 -------> |
         |                                       | <---------------------------------- |
         |                                       | --- 3. Refresh Token 저장 --------> |
         | <--- 4. 응답 (Access + Refresh Body)- |                                     |
         |                                       |                                     |
         | --- 5. API 요청 (+ Access Token) ---> | [JwtFilter]                         |
         |                                       |  - Access Token 검증                |
         |                                       |  - 유효하면 Controller 진입         |
         | <--- 6. 데이터 반환 ----------------- |                                     |
         |                                       |                                     |
         | --- 7. Access 만료 시 재발급 요청 --> |                                     |
         |        (Body에 Refresh Token 포함)    | --- 8. DB의 Refresh Token과 비교 -> |
         |                                       | <---------------------------------- |
         | --- 9. 새 Refresh Token DB 덮어쓰기-> |                                     |
         | <--- 10. 새 Access, Refresh 발급 ---- |                                     |


3. 핵심 코드 단계별 설명 (Body 반환 구조)
단계 1: 응답 반환 (AuthController)

Java
// AuthController.login()
@PostMapping("/login")
public ResponseEntity<ApiResponse<TokenResponse>> login(
        @RequestBody LoginRequest loginRequest) {

    TokenResponse response = authService.login(loginRequest);
    
    // 쿠키 생성 로직 제거됨
    // TokenResponse 객체 안에 accessToken과 refreshToken이 모두 들어있음
    return ResponseEntity.ok(ApiResponse.success(response)); 
}
작동 방식: 서버는 authService에서 두 개의 토큰을 발급받아 TokenResponse 객체에 담습니다. 그리고 HTTP 헤더 조작(쿠키 설정) 없이, 깔끔하게 JSON Body로만 클라이언트에게 전달합니다.

단계 2: 클라이언트의 토큰 수신 및 저장

JavaScript
// 프론트엔드 (예시)
const response = await api.post('/api/v1/auth/login', { email, password });
const { accessToken, refreshToken } = response.data.data;

// 둘 다 클라이언트 저장소에 직접 보관
localStorage.setItem('accessToken', accessToken);
localStorage.setItem('refreshToken', refreshToken); 
작동 방식: 클라이언트는 서버가 준 두 개의 출입증을 모두 받아서 자신의 저장소에 보관합니다.

단계 3: 액세스 토큰 만료 시 재발급 (Refresh)

Java
// AuthController.refresh()
@PostMapping("/refresh")
public ResponseEntity<ApiResponse<TokenResponse>> refresh(
        @RequestBody RefreshRequest refreshRequest) { // Body로 리프레시 토큰을 받음
    
    String oldRefreshToken = refreshRequest.getRefreshToken();
    TokenResponse response = authService.refreshToken(oldRefreshToken);
    
    return ResponseEntity.ok(ApiResponse.success(response));
}
작동 방식: 이전에는 클라이언트가 요청만 보내면 서버가 쿠키에서 리프레시 토큰을 꺼내서 확인했습니다. 하지만 이제는 클라이언트가 직접 Body(혹은 Header)에 리프레시 토큰을 담아서 보내야 합니다. 서버는 전달받은 토큰이 DB에 있는 토큰과 일치하는지 확인한 후, 새로운 TokenResponse(새 Access + 새 Refresh)를 만들어 다시 Body로 반환합니다.

```
