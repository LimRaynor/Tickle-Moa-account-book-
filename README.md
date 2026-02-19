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
│  │  ├─ config/
│  │  ├─ controller/
│  │  ├─ dto/
│  │  ├─ mapper/
│  │  ├─ model/
│  │  ├─ security/
│  │  └─ service/
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

- **config**: 보안/CORS/공통 설정 담당. 프론트와 "연결"이라기보다 서버의 요청 허용 규칙을 정하는 계층
- **controller**: 요청/응답 입구. RequestBody/RequestParam/PathVariable을 받아 Service 호출 후 응답 반환
- **service**: 비즈니스 로직 담당. 직접 SQL 실행보다 Mapper 호출로 DB 작업 수행
- **mapper**: Service 요청을 받아 MyBatis XML SQL 실행
- **model**: Repository가 아니라 DB 테이블과 매핑되는 도메인 객체(VO/Entity 성격)
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
- backend/src/main/java/com/tickle_moa/backend/config/CONFIG_GUIDE.md
- backend/src/main/java/com/tickle_moa/backend/controller/CONTROLLER_GUIDE.md
- backend/src/main/java/com/tickle_moa/backend/dto/DTO_GUIDE.md
- backend/src/main/java/com/tickle_moa/backend/service/SERVICE_GUIDE.md
- backend/src/main/java/com/tickle_moa/backend/mapper/MAPPER_GUIDE.md
- backend/src/main/java/com/tickle_moa/backend/model/MODEL_GUIDE.md
- backend/src/main/java/com/tickle_moa/backend/security/SECURITY_GUIDE.md
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
| `dto/LoginRequest` | 로그인 요청 DTO (email, password) |
| `dto/SignupRequest` | 회원가입 요청 DTO (name, email, password) |
| `dto/TokenResponse` | 로그인 응답 DTO (token, userId, name, email, role) |
| `security/JwtTokenProvider` | JWT 생성 / 검증 / 파싱 컴포넌트 |
| `security/CustomUserDetailsService` | 이메일 기반 UserDetails 조회 |
| `security/JwtAuthenticationFilter` | Authorization 헤더에서 토큰 추출 후 SecurityContext 등록 |
| `config/SecurityConfig` | JWT 필터 등록, `/api/auth/**` 공개, 나머지 인증 필요, Stateless 세션 |
| `service/AuthService` | `login()` 추가 — BCrypt 비밀번호 검증 포함 |
| `controller/AuthController` | 로그인 응답을 `TokenResponse`로 변경 (JWT 토큰 포함) |


**인증 흐름**

```
로그인 요청 (email + password)
  → 비밀번호 BCrypt 검증
  → JWT 발급 (유효기간 24시간)
  → TokenResponse 반환
  → localStorage에 token 저장
  → 이후 모든 API 요청에 Bearer 토큰 자동 첨부
```
