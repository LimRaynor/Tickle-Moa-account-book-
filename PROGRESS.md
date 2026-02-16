# 가계부 프로젝트 진행 현황

## 프로젝트 개요
- **목표**: 모놀리식 가계부 웹 애플리케이션
- **Backend**: Spring Boot 3.5.10 + MyBatis(메인) + Spring Security + MariaDB
- **Frontend**: Vue 3 + Pinia + Vue Router + Axios
- **베이스 패키지**: `com.ohgiraffers.backend`
- **패키지 구조**: 계층별 분리 (controller / service / mapper / model / dto / config / security)

## DB 정보
- **DB명**: account_book_db
- **계정**: account / book
- **포트**: localhost:3306

### 테이블
| 테이블 | 설명 |
|--------|------|
| users | 회원 (user_id, name, email, password, created_at, role) |
| accounts | 계좌 (account_id, user_id, name, balance, created_at) |
| transactions | 거래내역 (tran_id, account_id, type, category, amount, description, date, created_at) |
| untitled | 리프레시 토큰 (user_id, refresh_token) |

## 완료된 작업

### Phase 1: 백엔드 기본 구조 + 인증 API
- [x] MariaDB 연결 설정 (application.yml)
- [x] 패키지 구조 생성 (계층별: controller/service/mapper/model/dto/config/security)
- [x] Model 클래스 (User, Account, Transaction)
- [x] MyBatis Mapper 인터페이스 (UserMapper, AccountMapper, TransactionMapper)
- [x] MyBatis Mapper XML (SQL 작성)
- [x] AuthService (회원가입 로직 + BCrypt 암호화)
- [x] AuthController (POST /api/auth/signup, /api/auth/login)
- [x] SecurityConfig (CSRF 비활성화, permitAll 설정)
- [x] WebConfig (CORS - Vue localhost:5173 허용)

### Phase 2: 계좌 API
- [x] AccountService (목록조회, 생성, 삭제)
- [x] AccountController (GET/POST/DELETE /api/accounts)

### Phase 3: 거래 API
- [x] TransactionService (목록조회, 추가, 삭제)
- [x] TransactionController (GET/POST/DELETE /api/transactions)

### 빌드/실행 확인
- [x] `./gradlew build -x test` → BUILD SUCCESSFUL
- [x] `./gradlew bootRun` → 서버 정상 기동 (포트 8080, DB 연결 성공)

## 남은 작업

### Phase 4: 프론트엔드 (Vue)
- [ ] axios.js 설정 (baseURL, JWT 인터셉터)
- [ ] Pinia 인증 스토어 (auth.js)
- [ ] LoginView.vue 생성
- [ ] Signup.vue 폼 구현
- [ ] AccountView.vue 생성
- [ ] TransactionView.vue 생성
- [ ] 라우터 가드 (로그인 필요 페이지 보호)
- [ ] App.vue 네비게이션 수정

### 추후: JWT 인증
- [ ] build.gradle에 jjwt 라이브러리 추가 (완료)
- [ ] JwtTokenProvider (토큰 생성/검증)
- [ ] JwtAuthenticationFilter (요청 필터)
- [ ] CustomUserDetailsService (사용자 조회)
- [ ] DTO 구현 (LoginRequest, SignupRequest, TokenResponse)
- [ ] SecurityConfig에 JWT 필터 연결

## 현재 진행 상태
> **Phase 3 완료** — 백엔드 API 구현 완료, 프론트엔드 구현 대기 중
