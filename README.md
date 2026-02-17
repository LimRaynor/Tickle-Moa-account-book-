# Tickle-Moa Account Book

개인 가계부 웹 프로젝트입니다.
백엔드는 Spring Boot + MyBatis, 프론트엔드는 Vue 3 + Pinia + Axios로 구성되어 있습니다.

## 기술 스택

- Backend: Spring Boot 3.5.10, Spring Security, MyBatis
- Frontend: Vue 3, Vite, Pinia, Axios
- Database: MariaDB (account_book_db)
- Language: Java 21, JavaScript
- Build: Gradle, npm

## 프로젝트 구조

```text
account-book/
├─ backend/
│  ├─ src/main/java/com/ohgiraffers/backend/
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

```powershell
cd backend
.\gradlew.bat bootRun
```

## DB 정보

- Host: `localhost:3306`
- Database: `account_book_db`
- User/Password: `account` / `book`
- 상세 세팅: `LOCAL_DB_SETUP.md`

## API 요약

- POST `/api/auth/signup`
- POST `/api/auth/login`
- GET `/api/accounts?userId={userId}`
- POST `/api/accounts`
- DELETE `/api/accounts/{id}`
- GET `/api/transactions?accountId={accountId}`
- POST `/api/transactions`
- DELETE `/api/transactions/{id}`

## 요청 흐름

```text
Vue View -> Pinia Store -> Axios(api/axios.js)
        -> Spring Controller -> Service -> Mapper(XML) -> MariaDB
```

## 패키지별 가이드

### Backend
- `backend/src/main/java/com/ohgiraffers/backend/config/CONFIG_GUIDE.md`
- `backend/src/main/java/com/ohgiraffers/backend/controller/CONTROLLER_GUIDE.md`
- `backend/src/main/java/com/ohgiraffers/backend/dto/DTO_GUIDE.md`
- `backend/src/main/java/com/ohgiraffers/backend/service/SERVICE_GUIDE.md`
- `backend/src/main/java/com/ohgiraffers/backend/mapper/MAPPER_GUIDE.md`
- `backend/src/main/java/com/ohgiraffers/backend/model/MODEL_GUIDE.md`
- `backend/src/main/java/com/ohgiraffers/backend/security/SECURITY_GUIDE.md`
- `backend/src/main/resources/RESOURCES_GUIDE.md`

### Frontend
- `frontend/src/api/API_GUIDE.md`
- `frontend/src/router/ROUTER_GUIDE.md`
- `frontend/src/stores/STORE_GUIDE.md`
- `frontend/src/views/VIEWS_GUIDE.md`
- `frontend/src/assets/ASSETS_GUIDE.md`

## 테스트 진행 순서

### 1) 회원가입
![1. 회원가입](test/1.회원가입test.png)

### 2) 로그인
![2. 로그인](test/2.%20로그인test.png)

### 3) 계좌 등록
![3. 계좌 등록](test/3.계좌등록test.png)

### 4) 거래내역
![4. 거래내역](test/4.거래내역test.png)
![4-1. 거래내역 등록](test/4_1.거래내역등록test.png)

### 5) DB 등록 확인
![5. DB 등록 확인](test/5.DB등록확인test.png)
