package com.tickle_moa.backend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT("COMMON_001", "잘못된 입력입니다.", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("COMMON_002", "서버 내부 오류입니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // Auth
    DUPLICATE_EMAIL("AUTH_001", "중복 이메일입니다.", HttpStatus.CONFLICT),
    USER_NOT_FOUND("AUTH_002", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_PASSWORD("AUTH_003", "비밀번호가 틀렸습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("AUTH_004", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),

    // Account
    ACCOUNT_NOT_FOUND("ACCOUNT_001", "계좌를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // Transaction
    TRANSACTION_NOT_FOUND("TRANSACTION_001", "거래내역을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
