package com.tickle_moa.backend.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

@Data
// erd보고 넣기
// bigint==Long, DECIMAL==BigDecimal
public class Transaction {
	private Long tranId;
	private Long accountId;
	private String type;
	private String category;
	private BigDecimal amount; // 실제 돈의액수니 소수점까지
	private String description;
	private LocalDate date;
	private LocalDateTime createdAt;
}
