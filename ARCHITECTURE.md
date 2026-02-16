# Personal Finance Manager - 시스템 아키텍처

## 프로젝트 개요

사용자가 수입, 지출, 예산 및 재무 목표를 추적할 수 있도록 돕는 개인 재무 관리 웹 서비스입니다.

**기술 스택:**
- **백엔드:** Spring Boot (Java 17+)
- **프론트엔드:** Vue.js 3 (Composition API)
- **데이터베이스:** MariaDB
- **빌드 도구:** Gradle (백엔드), Vite (프론트엔드)

---

## 시스템 아키텍처

```
┌─────────────────────────────────────────────────────────────────┐
│                        클라이언트 계층                            │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │                    Vue.js 3 SPA                           │  │
│  │  ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────────┐   │  │
│  │  │  Views  │  │Components│  │  Store  │  │   Router    │   │  │
│  │  │         │  │         │  │ (Pinia) │  │(Vue Router) │   │  │
│  │  └─────────┘  └─────────┘  └─────────┘  └─────────────┘   │  │
│  └───────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ HTTP/REST (JSON)
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                         백엔드 계층                              │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │                    Spring Boot                            │  │
│  │                                                           │  │
│  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐     │  │
│  │  │  Controller  │  │   Service    │  │  Repository  │     │  │
│  │  │    계층      │──│    계층      │──│    계층      │     │  │
│  │  └──────────────┘  └──────────────┘  └──────────────┘     │  │
│  │         │                                    │            │  │
│  │  ┌──────────────┐              ┌──────────────────────┐   │  │
│  │  │   Security   │              │    JPA/Hibernate     │   │  │
│  │  │ (JWT 인증)   │              │                      │   │  │
│  │  └──────────────┘              └──────────────────────┘   │  │
│  └───────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ JDBC
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                        데이터 계층                               │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │                      MariaDB                              │  │
│  │                                                           │  │
│  │   users | accounts | transactions | categories | budgets  │  │
│  └───────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

---

## 기능 명세

### 1. 사용자 관리
| 기능 | 설명 |
|------|------|
| 회원가입 | 이메일/비밀번호 가입 및 유효성 검사 |
| 인증 | JWT 기반 로그인/로그아웃 |
| 프로필 | 사용자 프로필 조회 및 수정 |
| 비밀번호 재설정 | 이메일 기반 비밀번호 복구 |

### 2. 계좌 관리
| 기능 | 설명 |
|------|------|
| 계좌 생성 | 은행 계좌, 현금, 신용카드 등 추가 |
| 계좌 유형 | 입출금, 저축, 신용카드, 현금, 투자 |
| 잔액 추적 | 거래 내역 기반 자동 잔액 계산 |
| 다중 계좌 | 사용자당 무제한 계좌 지원 |

### 3. 거래 관리
| 기능 | 설명 |
|------|------|
| 거래 추가 | 수입, 지출 또는 계좌 간 이체 |
| 카테고리 분류 | 거래에 카테고리 지정 |
| 반복 거래 | 반복 거래 설정 |
| 검색 및 필터 | 날짜, 카테고리, 계좌, 금액별 필터링 |
| 일괄 가져오기 | 은행 명세서 CSV 가져오기 |

### 4. 카테고리 관리
| 기능 | 설명 |
|------|------|
| 기본 카테고리 | 미리 정의된 카테고리 (음식, 교통 등) |
| 사용자 정의 카테고리 | 사용자 지정 카테고리 생성 |
| 카테고리 그룹 | 카테고리 그룹화 (필수, 엔터테인먼트 등) |
| 아이콘 및 색상 | 시각적 커스터마이징 |

### 5. 예산 관리
| 기능 | 설명 |
|------|------|
| 월별 예산 | 카테고리별 지출 한도 설정 |
| 예산 추적 | 실시간 지출 대 예산 비교 |
| 알림 | 예산 초과 시 알림 |
| 예산 이력 | 과거 예산 성과 조회 |

### 6. 보고서 및 분석
| 기능 | 설명 |
|------|------|
| 지출 요약 | 월별/연별 지출 내역 |
| 수입 대 지출 | 순수입 시각화 |
| 카테고리 분석 | 카테고리별 지출 차트 |
| 추세 분석 | 월별 비교 |
| 내보내기 | PDF/CSV 보고서 생성 |

### 7. 대시보드
| 기능 | 설명 |
|------|------|
| 개요 | 전체 계좌 총 잔액 |
| 최근 거래 | 최근 10개 거래 |
| 예산 현황 | 이번 달 예산 개요 |
| 빠른 작업 | 거래 추가, 이체 |

---

## 데이터베이스 스키마

```
┌─────────────────┐       ┌─────────────────┐
│     users       │       │    accounts     │
├─────────────────┤       ├─────────────────┤
│ id (PK)         │───┐   │ id (PK)         │
│ email           │   │   │ user_id (FK)    │───┐
│ password_hash   │   └──▶│ name            │   │
│ name            │       │ type            │   │
│ created_at      │       │ currency        │   │
│ updated_at      │       │ initial_balance │   │
└─────────────────┘       │ created_at      │   │
                          └─────────────────┘   │
                                                │
┌─────────────────┐       ┌─────────────────┐   │
│   categories    │       │  transactions   │   │
├─────────────────┤       ├─────────────────┤   │
│ id (PK)         │───┐   │ id (PK)         │   │
│ user_id (FK)    │   │   │ account_id (FK) │◀──┘
│ name            │   │   │ category_id(FK) │◀──┘
│ type            │   │   │ type            │
│ icon            │   │   │ amount          │
│ color           │   │   │ description     │
│ parent_id (FK)  │───┘   │ date            │
│ is_default      │       │ created_at      │
└─────────────────┘       └─────────────────┘

┌─────────────────┐       ┌─────────────────┐
│    budgets      │       │   transfers     │
├─────────────────┤       ├─────────────────┤
│ id (PK)         │       │ id (PK)         │
│ user_id (FK)    │       │ from_account_id │
│ category_id(FK) │       │ to_account_id   │
│ amount          │       │ amount          │
│ month           │       │ date            │
│ year            │       │ description     │
└─────────────────┘       │ created_at      │
                          └─────────────────┘

┌─────────────────────┐
│ recurring_transactions│
├─────────────────────┤
│ id (PK)             │
│ user_id (FK)        │
│ account_id (FK)     │
│ category_id (FK)    │
│ amount              │
│ type                │
│ description         │
│ frequency           │
│ next_date           │
│ is_active           │
└─────────────────────┘
```

---

## API 엔드포인트

### 인증
| 메서드 | 엔드포인트 | 설명 |
|--------|-----------|------|
| POST | `/api/auth/register` | 회원가입 |
| POST | `/api/auth/login` | 로그인 |
| POST | `/api/auth/logout` | 로그아웃 |
| POST | `/api/auth/refresh` | JWT 토큰 갱신 |
| POST | `/api/auth/password-reset` | 비밀번호 재설정 요청 |

### 사용자
| 메서드 | 엔드포인트 | 설명 |
|--------|-----------|------|
| GET | `/api/users/me` | 현재 사용자 프로필 조회 |
| PUT | `/api/users/me` | 사용자 프로필 수정 |
| PUT | `/api/users/me/password` | 비밀번호 변경 |

### 계좌
| 메서드 | 엔드포인트 | 설명 |
|--------|-----------|------|
| GET | `/api/accounts` | 전체 계좌 목록 |
| POST | `/api/accounts` | 계좌 생성 |
| GET | `/api/accounts/{id}` | 계좌 상세 조회 |
| PUT | `/api/accounts/{id}` | 계좌 수정 |
| DELETE | `/api/accounts/{id}` | 계좌 삭제 |
| GET | `/api/accounts/{id}/balance` | 현재 잔액 조회 |

### 거래
| 메서드 | 엔드포인트 | 설명 |
|--------|-----------|------|
| GET | `/api/transactions` | 거래 목록 (필터 적용) |
| POST | `/api/transactions` | 거래 생성 |
| GET | `/api/transactions/{id}` | 거래 상세 조회 |
| PUT | `/api/transactions/{id}` | 거래 수정 |
| DELETE | `/api/transactions/{id}` | 거래 삭제 |
| POST | `/api/transactions/import` | CSV 가져오기 |

### 카테고리
| 메서드 | 엔드포인트 | 설명 |
|--------|-----------|------|
| GET | `/api/categories` | 전체 카테고리 목록 |
| POST | `/api/categories` | 카테고리 생성 |
| PUT | `/api/categories/{id}` | 카테고리 수정 |
| DELETE | `/api/categories/{id}` | 카테고리 삭제 |

### 예산
| 메서드 | 엔드포인트 | 설명 |
|--------|-----------|------|
| GET | `/api/budgets` | 기간별 예산 목록 |
| POST | `/api/budgets` | 예산 생성/수정 |
| GET | `/api/budgets/{id}` | 예산 상세 조회 |
| DELETE | `/api/budgets/{id}` | 예산 삭제 |
| GET | `/api/budgets/summary` | 예산 대 실제 비교 |

### 보고서
| 메서드 | 엔드포인트 | 설명 |
|--------|-----------|------|
| GET | `/api/reports/summary` | 재무 요약 |
| GET | `/api/reports/by-category` | 카테고리별 지출 |
| GET | `/api/reports/trends` | 월별 추세 |
| GET | `/api/reports/export` | PDF/CSV 내보내기 |

### 대시보드
| 메서드 | 엔드포인트 | 설명 |
|--------|-----------|------|
| GET | `/api/dashboard` | 대시보드 데이터 조회 |

---

## 프로젝트 구조

### 백엔드 (Spring Boot)
```
backend/
├── src/main/java/com/financemanager/
│   ├── FinanceManagerApplication.java
│   ├── config/
│   │   ├── SecurityConfig.java
│   │   ├── JwtConfig.java
│   │   └── CorsConfig.java
│   ├── controller/
│   │   ├── AuthController.java
│   │   ├── AccountController.java
│   │   ├── TransactionController.java
│   │   ├── CategoryController.java
│   │   ├── BudgetController.java
│   │   └── ReportController.java
│   ├── service/
│   │   ├── AuthService.java
│   │   ├── AccountService.java
│   │   ├── TransactionService.java
│   │   ├── CategoryService.java
│   │   ├── BudgetService.java
│   │   └── ReportService.java
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── AccountRepository.java
│   │   ├── TransactionRepository.java
│   │   ├── CategoryRepository.java
│   │   └── BudgetRepository.java
│   ├── entity/
│   │   ├── User.java
│   │   ├── Account.java
│   │   ├── Transaction.java
│   │   ├── Category.java
│   │   └── Budget.java
│   ├── dto/
│   │   ├── request/
│   │   └── response/
│   ├── security/
│   │   ├── JwtTokenProvider.java
│   │   ├── JwtAuthenticationFilter.java
│   │   └── UserPrincipal.java
│   └── exception/
│       ├── GlobalExceptionHandler.java
│       └── CustomExceptions.java
├── src/main/resources/
│   ├── application.yml
│   └── db/migration/
└── build.gradle
```

### 프론트엔드 (Vue.js)
```
frontend/
├── src/
│   ├── main.js
│   ├── App.vue
│   ├── views/
│   │   ├── Dashboard.vue
│   │   ├── Accounts.vue
│   │   ├── Transactions.vue
│   │   ├── Categories.vue
│   │   ├── Budgets.vue
│   │   ├── Reports.vue
│   │   └── auth/
│   │       ├── Login.vue
│   │       └── Register.vue
│   ├── components/
│   │   ├── common/
│   │   ├── accounts/
│   │   ├── transactions/
│   │   ├── budgets/
│   │   └── charts/
│   ├── stores/
│   │   ├── auth.js
│   │   ├── accounts.js
│   │   ├── transactions.js
│   │   └── budgets.js
│   ├── router/
│   │   └── index.js
│   ├── api/
│   │   ├── axios.js
│   │   ├── auth.js
│   │   ├── accounts.js
│   │   └── transactions.js
│   ├── composables/
│   └── utils/
├── index.html
├── vite.config.js
└── package.json
```

---

## 개발 로그

> **단계별 MVP 개발 안내는 [MVP_GUIDE.md](./MVP_GUIDE.md)를 참조하세요.**

### MVP 단계 (현재)
- [ ] 백엔드: Spring Boot 프로젝트 설정
- [ ] 백엔드: MariaDB 연결
- [ ] 백엔드: User/Account/Transaction 엔티티
- [ ] 백엔드: JWT 인증
- [ ] 백엔드: 핵심 API 엔드포인트
- [ ] 프론트엔드: Vue + Vite 설정
- [ ] 프론트엔드: 인증 페이지 (로그인/회원가입)
- [ ] 프론트엔드: 계좌 및 거래 페이지
- [ ] 프론트엔드: 대시보드
- [ ] 통합: 전체 흐름 동작 확인

### MVP 이후 단계

#### 1단계: 기능 강화
- [ ] 거래 필터링 및 검색
- [ ] 아이콘/색상이 있는 사용자 정의 카테고리
- [ ] 계좌 유형 (입출금, 저축, 신용카드)
- [ ] 거래 페이지네이션

#### 2단계: 예산 관리
- [ ] 예산 엔티티 및 서비스
- [ ] 예산 CRUD API
- [ ] 예산 대 실제 추적
- [ ] 예산 알림

#### 3단계: 보고서 및 분석
- [ ] 보고서 생성 서비스
- [ ] Chart.js 통합
- [ ] 지출 내역 차트
- [ ] 월별 추세
- [ ] CSV/PDF 내보내기

#### 4단계: 고급 기능
- [ ] 반복 거래
- [ ] CSV 가져오기
- [ ] 비밀번호 재설정 (이메일)
- [ ] 다중 통화 지원

#### 5단계: 마무리 및 테스트
- [ ] 서비스 단위 테스트
- [ ] API 통합 테스트
- [ ] Cypress E2E 테스트
- [ ] 성능 최적화

---

## 설정

### 백엔드 (application.yml)
```yaml
spring:
  datasource:
    url: jdbc:mariadb://localhost:3306/finance_manager
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: org.mariadb.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: validate
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MariaDBDialect

jwt:
  secret: ${JWT_SECRET}
  expiration: 86400000  # 24시간
  refresh-expiration: 604800000  # 7일

server:
  port: 8080
```

### 프론트엔드 (.env)
```
VITE_API_BASE_URL=http://localhost:8080/api
```

---

## 의존성

### 백엔드 (build.gradle)
```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.mariadb.jdbc:mariadb-java-client'
    implementation 'io.jsonwebtoken:jjwt-api:0.12.3'
    runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.12.3'
    runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.12.3'
    implementation 'org.flywaydb:flyway-core'
    implementation 'org.flywaydb:flyway-mysql'
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
}
```

### 프론트엔드 (package.json)
```json
{
  "dependencies": {
    "vue": "^3.4.0",
    "vue-router": "^4.2.0",
    "pinia": "^2.1.0",
    "axios": "^1.6.0",
    "chart.js": "^4.4.0",
    "vue-chartjs": "^5.3.0"
  },
  "devDependencies": {
    "@vitejs/plugin-vue": "^5.0.0",
    "vite": "^5.0.0",
    "tailwindcss": "^3.4.0"
  }
}
```
