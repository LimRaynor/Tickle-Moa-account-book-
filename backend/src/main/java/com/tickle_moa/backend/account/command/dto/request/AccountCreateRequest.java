package com.tickle_moa.backend.account.command.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class
AccountCreateRequest {

    private Long userId;
    private String name;
    private BigDecimal balance;
}
