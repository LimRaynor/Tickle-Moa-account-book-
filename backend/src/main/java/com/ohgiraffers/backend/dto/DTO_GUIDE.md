# DTO Package Guide

## 역할
- 요청/응답에 사용하는 데이터 구조를 정의합니다.

## 현재 파일
- `LoginRequest.java`
- `SignupRequest.java`
- `TokenResponse.java`

## 참고
- 현재 일부 컨트롤러는 DTO 대신 `model` 객체를 직접 사용 중입니다.
- 점진적으로 Controller 입력/출력을 DTO로 통일하면 API 안정성이 좋아집니다.
