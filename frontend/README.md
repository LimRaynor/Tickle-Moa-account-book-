# 가계부 프론트엔드 - 전체 흐름 가이드

## 전체 흐름도
```
[브라우저] → URL 입력 or 링크 클릭
       ↓
[Router]        → URL을 분석해서 어떤 View를 보여줄지 결정
       ↓
[View]          → 화면 렌더링 + 사용자 입력 수집
       ↓ store 함수 호출
[Store (Pinia)] → 데이터 보관 + API 호출 로직
       ↓ api.get(), api.post() 등
[axios 인스턴스] → baseURL 붙여서 HTTP 요청 전송
       ↓
[백엔드 8080]   → Controller → Service → Mapper → DB
       ↓
[axios 응답]    → res.data로 결과 수신
       ↓
[Store]         → ref에 데이터 저장 → 화면 자동 갱신
```

## 사용자 시나리오별 흐름

### 1. 회원가입
```
Signup.vue → 이름/이메일/비밀번호 입력
     ↓ api.post('/api/auth/signup', { name, email, password })
백엔드 → DB에 유저 저장
     ↓
alert('회원가입 성공!')
```

### 2. 로그인
```
LoginView.vue → 이메일/비밀번호 입력
     ↓ api.post('/api/auth/login', { email, password })
백엔드 → User 객체 반환 { userId, name, email }
     ↓
authStore.setUser(res.data) → store에 유저 정보 저장
     ↓
router.push('/') → 메인 페이지로 이동
```

### 3. 계좌 관리
```
AccountView.vue 열림
     ↓ onMounted
accountStore.fetchAccounts(authStore.user.userId)
     ↓ api.get('/api/accounts?userId=1')
백엔드 → 계좌 목록 반환 [{ accountId, name, balance }, ...]
     ↓
accounts ref에 저장 → v-for로 화면에 목록 표시
     ↓
[추가] → addAccount({ userId, name, balance:0 }) → fetchAccounts 재호출
[삭제] → deleteAccount(id) → fetchAccounts 재호출
```

### 4. 거래 내역
```
TransactionView.vue 열림
     ↓ onMounted
accountStore.fetchAccounts → 계좌 드롭다운 표시
     ↓ 사용자가 계좌 선택 (@change)
transactionStore.fetchTransactions(accountId)
     ↓ api.get('/api/transactions?accountId=3')
백엔드 → 거래 목록 반환 [{ tranId, type, category, amount, date }, ...]
     ↓
transactions ref에 저장 → v-for로 목록 표시
     ↓
[추가] → addTransaction({ accountId, type, category, amount, ... }) → fetchTransactions 재호출
[삭제] → deleteTransaction(id) → fetchTransactions 재호출
```

## 패키지별 역할 요약

| 패키지 | 역할 | 핵심 개념 | 상세 |
|--------|------|-----------|------|
| **api** | axios 인스턴스 (HTTP 요청 설정) | `axios.create()`, `baseURL` | [API_GUIDE.md](src/api/API_GUIDE.md) |
| **stores** | 데이터 보관 + API 호출 캡슐화 | `defineStore`, `ref`, `async` | [STORE_GUIDE.md](src/stores/STORE_GUIDE.md) |
| **views** | 페이지 화면 (입력 폼 + 목록 표시) | `v-model`, `v-for`, `@submit` | [VIEWS_GUIDE.md](src/views/VIEWS_GUIDE.md) |
| **router** | URL ↔ 화면 매핑 | `createRouter`, `RouterView` | [ROUTER_GUIDE.md](src/router/ROUTER_GUIDE.md) |

## 백엔드 ↔ 프론트엔드 대응표

| 백엔드 | 프론트엔드 | 공통점 |
|--------|-----------|--------|
| Controller | View | 요청/입력의 입구 |
| Service | Store | 비즈니스 로직 / 데이터 관리 |
| Mapper | axios (api/) | 외부 시스템(DB/백엔드)과 통신 |
| Model | ref() 변수 | 데이터 운반 그릇 |
| Config (CORS) | vite.config.js | 환경 설정 |

## 코딩 순서 (아래에서 위로)
```
1. axios 설정   → baseURL, 인터셉터 자리 확보
2. Store 생성   → ref 선언 + API 호출 함수 작성
3. View 작성    → 화면 폼 + store 연결
4. Router 등록  → URL ↔ View 매핑
5. App.vue      → nav에 링크 추가
```

## 실행 방법
```bash
cd frontend && npm run dev         # http://localhost:5173
cd backend && ./gradlew bootRun    # http://localhost:8080 (동시에 실행)
```

## 테스트 순서
```
1. 회원가입 (/signup) → 이름, 이메일, 비밀번호 입력
2. 로그인 (/login) → 가입한 이메일로 로그인
3. 계좌 생성 (/accounts) → 계좌명 입력해서 추가
4. 거래 추가 (/transactions) → 계좌 선택 후 거래 입력
```
