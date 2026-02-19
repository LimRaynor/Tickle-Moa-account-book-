package com.tickle_moa.backend.transaction.command.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionCommandResponse {

    private final Long tranId;
    private final String message;
}
