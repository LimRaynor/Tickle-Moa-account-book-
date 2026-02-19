package com.tickle_moa.backend.account.query.service;

import com.tickle_moa.backend.account.query.dto.response.AccountDTO;
import com.tickle_moa.backend.account.query.dto.response.AccountListResponse;
import com.tickle_moa.backend.account.query.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountQueryService {

    private final AccountMapper accountMapper;

    public AccountListResponse getAccountsByUserId(Long userId) {
        List<AccountDTO> accounts = accountMapper.findByUserId(userId);
        return AccountListResponse.builder()
                .accounts(accounts)
                .totalCount(accounts.size())
                .build();
    }
}
