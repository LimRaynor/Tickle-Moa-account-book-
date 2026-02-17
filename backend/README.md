# Backend 패키지 구조 가이드

## 전체 요청 흐름

```text
[Vue Frontend] -> HTTP 요청(axios)
              -> [Config] (CORS/보안 규칙)
              -> [Controller] (요청 수신/응답 반환)
              -> [Service] (비즈니스 로직)
              -> [Mapper + XML] (SQL 실행)
              -> [MariaDB] (데이터 저장/조회)
```

## 패키지별 역할

- `config`
  - 보안/CORS/공통 설정 담당
  - 서버가 어떤 요청을 허용할지 규칙을 정의
- `controller`
  - 요청/응답 입구
  - `RequestBody/RequestParam/PathVariable` 수신 후 Service 호출, 응답 반환
- `service`
  - 비즈니스 로직 담당
  - Mapper를 통해 DB 작업 수행
- `mapper`
  - Service에서 호출되는 데이터 접근 계층
  - MyBatis XML SQL 실행 연결
- `model`
  - DB 테이블과 매핑되는 도메인 객체
  - Repository가 아니라 데이터 구조(VO/Entity 성격)
- `MariaDB`
  - 데이터 저장/조회/수정/삭제 수행

## 패키지 상세 문서

- `src/main/java/com/tickle_moa/backend/config/CONFIG_GUIDE.md`
- `src/main/java/com/tickle_moa/backend/controller/CONTROLLER_GUIDE.md`
- `src/main/java/com/tickle_moa/backend/dto/DTO_GUIDE.md`
- `src/main/java/com/tickle_moa/backend/service/SERVICE_GUIDE.md`
- `src/main/java/com/tickle_moa/backend/mapper/MAPPER_GUIDE.md`
- `src/main/java/com/tickle_moa/backend/model/MODEL_GUIDE.md`
- `src/main/java/com/tickle_moa/backend/security/SECURITY_GUIDE.md`
- `src/main/resources/RESOURCES_GUIDE.md`

## API 엔드포인트

### 인증 (`/api/auth`)
- `POST /api/auth/signup`
- `POST /api/auth/login`

### 계좌 (`/api/accounts`)
- `GET /api/accounts?userId=1`
- `POST /api/accounts`
- `DELETE /api/accounts/{id}`

### 거래 (`/api/transactions`)
- `GET /api/transactions?accountId=1`
- `POST /api/transactions`
- `DELETE /api/transactions/{id}`

## 실행

```bash
cd backend
./gradlew bootRun
```
