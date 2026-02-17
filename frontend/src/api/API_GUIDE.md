# API Package Guide

## 역할
- Axios 인스턴스를 공통 설정으로 제공

## 주요 파일
- `axios.js`

## 현재 설정
- `baseURL`: `http://localhost:8080`

## 흐름
```text
View/Store -> import api -> api.get/post/delete -> Backend API
```

## 확장 포인트
- JWT 적용 시 request interceptor에서 `Authorization` 헤더를 공통 추가
