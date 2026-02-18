# Docker 실행 가이드

## 필요한 것

- **Docker Desktop** 설치 ([https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop))
- 그 외 Java, Node.js, MariaDB 설치 불필요

---

## 처음 실행할 때

```bash
# 1. 프로젝트 폴더로 이동
cd account-book

# 2. 빌드 + 실행 (처음 한 번만, 이미지 다운로드로 5~10분 걸림)
docker-compose up --build
```

완료되면:
- 프론트엔드: http://localhost:5173
- 백엔드 API: http://localhost:8080

---

## DB 초기화 (처음 실행 시 필수)

컨테이너가 처음 뜨면 DB는 비어있습니다.
테이블을 생성하려면 아래 SQL을 실행해야 합니다.

```bash
# 컨테이너가 실행 중인 상태에서
docker exec -i account-book-db-1 mariadb -u account -pbook account_book_db < init.sql
```

> `init.sql` 파일이 없다면 기존 환경에서 아래 명령으로 추출:
> ```bash
> docker exec account-book-db-1 mariadb-dump -u account -pbook account_book_db > init.sql
> ```

---

## 두 번째 실행부터

```bash
# 빌드 없이 바로 시작 (빠름)
docker-compose up

# 백그라운드로 실행
docker-compose up -d
```

---

## 종료

```bash
docker-compose down
```

> 데이터는 볼륨에 보존되므로 다음에 다시 `up` 하면 그대로 남아있습니다.

---

## 코드 수정 후 재빌드

```bash
# 이미지 다시 빌드 후 실행
docker-compose up --build
```

---

## 자주 쓰는 명령어

| 명령어 | 설명 |
|--------|------|
| `docker-compose up --build` | 빌드 + 실행 |
| `docker-compose up -d` | 백그라운드 실행 |
| `docker-compose down` | 중지 + 컨테이너 제거 |
| `docker-compose logs -f backend` | 백엔드 로그 실시간 확인 |
| `docker-compose logs -f frontend` | 프론트 로그 실시간 확인 |
| `docker ps` | 실행 중인 컨테이너 확인 |

---

## 구성 요약

```
account-book/
├── docker-compose.yml       ← 전체 구성
├── backend/
│   └── Dockerfile           ← Gradle 빌드 → JRE 실행
└── frontend/
    └── Dockerfile           ← npm install → vite dev 서버
```

| 서비스 | 이미지 | 포트 |
|--------|--------|------|
| db | mariadb:10.11 | 내부 전용 |
| backend | eclipse-temurin:21 | 8080 |
| frontend | node:22 | 5173 |
