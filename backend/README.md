# 가계부 백엔드 - 패키지 구조 가이드

## 전체 요청 흐름
```
[Vue 프론트엔드] → HTTP 요청 (axios)
       ↓
[Config]       → 서버 시작 시 보안/CORS 규칙 로딩
       ↓
[Controller]   → 요청 접수, 응답 반환 (REST API 입구)
       ↓
[Service]      → 비즈니스 로직 (판단 + 가공)
       ↓
[Mapper]       → 인터페이스 (메서드 선언)
       ↓
[Mapper XML]   → 실제 SQL 실행
       ↓
[Model]        → DB ↔ Java 데이터 운반 그릇
       ↓
[MariaDB]      → 데이터 저장/조회
```

## 패키지별 역할 요약

| 패키지 | 역할 | 핵심 어노테이션 | 상세 |
|--------|------|----------------|------|
| **model** | DB 테이블 ↔ Java 객체 매핑 | `@Data` | [MODEL_GUIDE.md](src/main/java/com/ohgiraffers/backend/model/MODEL_GUIDE.md) |
| **mapper** | DB 작업 메서드 선언 (interface) | `@Mapper` | [MAPPER_GUIDE.md](src/main/java/com/ohgiraffers/backend/mapper/MAPPER_GUIDE.md) |
| **mappers XML** | 실제 SQL 작성 | `<select>` `<insert>` `<delete>` | resources/mappers/ |
| **service** | 비즈니스 로직 (판단/가공) | `@Service` `@Autowired` | [SERVICE_GUIDE.md](src/main/java/com/ohgiraffers/backend/service/SERVICE_GUIDE.md) |
| **controller** | HTTP 요청 접수 + JSON 응답 | `@RestController` `@RequestMapping` | [CONTROLLER_GUIDE.md](src/main/java/com/ohgiraffers/backend/controller/CONTROLLER_GUIDE.md) |
| **config** | Security, CORS 설정 | `@Configuration` `@Bean` | [CONFIG_GUIDE.md](src/main/java/com/ohgiraffers/backend/config/CONFIG_GUIDE.md) |
| **security** | JWT 인증 (미구현) | - | 추후 작성 |
| **dto** | 요청/응답 전용 객체 (미구현) | - | 추후 작성 |

## 코딩 순서 (아래에서 위로)
```
1. Model      → DB 테이블에 맞는 필드 선언 (그릇 만들기)
2. Mapper     → 인터페이스에 메서드 선언 (어떤 DB 작업이 있는지)
3. XML        → 실제 SQL 작성 (SELECT, INSERT, DELETE)
4. Service    → Mapper를 호출하면서 판단/가공 로직 추가
5. Controller → URL 매핑하고 Service 호출
6. Config     → 보안, CORS 등 서버 설정
```

## API 엔드포인트

### 인증 (/api/auth)
| 메서드 | 경로 | 설명 |
|--------|------|------|
| POST | `/api/auth/signup` | 회원가입 |
| POST | `/api/auth/login` | 로그인 |

### 계좌 (/api/accounts)
| 메서드 | 경로 | 설명 |
|--------|------|------|
| GET | `/api/accounts?userId=1` | 내 계좌 목록 |
| POST | `/api/accounts` | 계좌 생성 |
| DELETE | `/api/accounts/{id}` | 계좌 삭제 |

### 거래 (/api/transactions)
| 메서드 | 경로 | 설명 |
|--------|------|------|
| GET | `/api/transactions?accountId=1` | 거래 목록 |
| POST | `/api/transactions` | 거래 추가 |
| DELETE | `/api/transactions/{id}` | 거래 삭제 |

## 실행 방법
```bash
cd backend && ./gradlew bootRun    # http://localhost:8080
cd frontend && npm run dev          # http://localhost:5173
```
