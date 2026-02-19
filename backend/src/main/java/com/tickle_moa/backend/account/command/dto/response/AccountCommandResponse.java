package com.tickle_moa.backend.account.command.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountCommandResponse {

    private final Long accountId;
    private final String message;
}
