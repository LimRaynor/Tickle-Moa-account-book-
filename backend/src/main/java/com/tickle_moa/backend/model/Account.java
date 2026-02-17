package com.tickle_moa.backend.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
// erd보고 넣기
// bigint==Long, DECIMAL==BigDecimal
public class Account {
	private Long accountId;
	private Long userId;
	private String name;
	private BigDecimal balance; // 은행권이라 소수점까지
	private LocalDateTime createdAt;
}
