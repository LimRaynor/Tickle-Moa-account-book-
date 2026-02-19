package com.tickle_moa.backend.account.query.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {

    private Long accountId;
    private Long userId;
    private String name;
    private BigDecimal balance;
    private LocalDateTime createdAt;
}
