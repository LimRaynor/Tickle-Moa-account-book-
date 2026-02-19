package com.tickle_moa.backend.transaction.query.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {

    private Long tranId;
    private Long accountId;
    private String type;
    private String category;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private LocalDateTime createdAt;
}
