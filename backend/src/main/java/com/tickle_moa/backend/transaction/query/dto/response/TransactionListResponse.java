package com.tickle_moa.backend.transaction.query.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TransactionListResponse {

    private final List<TransactionDTO> transactions;
    private final int totalCount;
}
