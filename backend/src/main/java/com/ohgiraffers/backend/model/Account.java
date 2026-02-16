package com.ohgiraffers.backend.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
// accounts 테이블과 매핑되는 VO 클래스
// 필드: accountId, userId, name, balance, createdAt
public class Account {
	private Long accountId;
	private Long userId;
	private String name;
	private BigDecimal balance; // 은행권이라 소수점까지
	private LocalDateTime createdAt;
}
