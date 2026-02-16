package com.ohgiraffers.backend.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

@Data
// transactions 테이블과 매핑되는 VO 클래스
// 필드: tranId, accountId, type, category, amount, description, date, createdAt
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
