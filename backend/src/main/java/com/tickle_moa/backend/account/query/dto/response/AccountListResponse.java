package com.tickle_moa.backend.account.query.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AccountListResponse {

    private final List<AccountDTO> accounts;
    private final int totalCount;
}
