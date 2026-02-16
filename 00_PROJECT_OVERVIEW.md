# 가계부 프로젝트 개요

## 1. 프로젝트 요약
- 목표: 개인 가계부 웹 앱(수입/지출 관리).
- 모노레포 구조:
  - `backend`: Spring Boot + MyBatis + MariaDB API 서버
  - `frontend`: Vue 3 + Vite 클라이언트
- 현재 상태: 백엔드 CRUD API는 대부분 연결되어 있고, 프론트엔드는 기본 뼈대만 있는 상태로 API 연동은 아직 미완료.

## 2. 기술 스택
- 백엔드
  - Java 21
  - Spring Boot 3.5.10
  - Spring Security
  - MyBatis 3.0.5
  - MariaDB 드라이버
  - JJWT (의존성 추가됨, 구현은 미완료)
- 프론트엔드
  - Vue 3.5
  - Vite 7
  - Vue Router
  - Pinia
  - Axios

## 3. 디렉터리 구조
- 루트 문서:
  - `README.md`, `PROGRESS.md`, `ARCHITECTURE.md`, `MVP_GUIDE.md`, `mariaDB.md`
- 백엔드:
  - 컨트롤러: `backend/src/main/java/com/ohgiraffers/backend/controller`
  - 서비스: `backend/src/main/java/com/ohgiraffers/backend/service`
  - 매퍼 인터페이스: `backend/src/main/java/com/ohgiraffers/backend/mapper`
  - SQL XML: `backend/src/main/resources/mappers`
  - 설정/보안: `backend/src/main/java/com/ohgiraffers/backend/config`, `backend/src/main/java/com/ohgiraffers/backend/security`
  - 애플리케이션 설정: `backend/src/main/resources/application.yml`
- 프론트엔드:
  - 라우터: `frontend/src/router/index.js`
  - 뷰: `frontend/src/views`
  - API 레이어: `frontend/src/api/axios.js` (현재 비어 있음)
  - 앱 셸: `frontend/src/App.vue`

## 4. 구현된 API 현황 (백엔드)
- 인증(Auth)
  - `POST /api/auth/signup`
  - `POST /api/auth/login`
- 계좌(Accounts)
  - `GET /api/accounts?userId={id}`
  - `POST /api/accounts`
  - `DELETE /api/accounts/{id}`
- 거래(Transactions)
  - `GET /api/transactions?accountId={id}`
  - `POST /api/transactions`
  - `DELETE /api/transactions/{id}`

## 5. 데이터 모델 범위
- `User`: userId, name, email, password, createdAt, role
- `Account`: accountId, userId, name, balance, createdAt
- `Transaction`: tranId, accountId, type, category, amount, description, date, createdAt

## 6. 실행 설정
- 백엔드 서버: `localhost:8080`
- 프론트 개발 서버: `localhost:5173`
- `application.yml` DB 설정:
  - URL: `jdbc:mariadb://localhost:3306/account_book_db`
  - Username: `account`
  - Password: `book`
- CORS는 `http://localhost:5173`에 대해 허용되어 있음.

## 7. 현재 공백 / 리스크
- 프론트 라우트에서 없는 파일을 참조:
  - `frontend/src/router/index.js`에서 `LoginView.vue`, `AccountView.vue`를 import하지만 `frontend/src/views`에 파일이 없음.
- `frontend/src/api/axios.js`가 비어 있음(API 클라이언트/인터셉터 미구현).
- `frontend/src/App.vue` 템플릿 문자열이 깨져 있어(닫는 태그/문자열 오류) 컴파일 또는 런타임 문제 가능성이 큼.
- 보안 설정이 현재 모두 오픈 상태:
  - `SecurityConfig`가 모든 요청을 허용(`anyRequest().permitAll()`).
- JWT 관련 클래스는 뼈대만 존재:
  - `JwtTokenProvider`, `JwtAuthenticationFilter`, `CustomUserDetailsService` 미구현.
- 기존 한글 문서 일부가 터미널에서 인코딩 깨짐으로 보임(에디터 인코딩에 따라 가독성 차이 가능).

## 8. 권장 다음 작업 순서
1. 프론트 컴파일 블로커 먼저 해결(`App.vue`, 누락된 뷰 파일).
2. `axios.js` 및 기본 인증 스토어 흐름 구현.
3. 로그인 로직 강화(실제 비밀번호 검증, 토큰 발급).
4. JWT provider/filter/user-details 구현 후 `SecurityConfig`에 연동.
5. 보호 페이지 라우트 가드 추가.
6. auth/account/transaction API 기본 통합 테스트 추가.

## 9. 빠른 실행 명령어
```bash
# backend
cd backend
./gradlew bootRun

# frontend
cd frontend
npm install
npm run dev
```
