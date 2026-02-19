package com.tickle_moa.backend.transaction.command.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TransactionCreateRequest {

    private Long accountId;
    private String type;
    private String category;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
}
