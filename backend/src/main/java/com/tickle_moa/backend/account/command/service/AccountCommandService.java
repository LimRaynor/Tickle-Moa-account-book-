package com.tickle_moa.backend.account.command.service;

import com.tickle_moa.backend.account.command.dto.request.AccountCreateRequest;
import com.tickle_moa.backend.account.command.dto.response.AccountCommandResponse;
import com.tickle_moa.backend.account.command.entity.Account;
import com.tickle_moa.backend.account.command.repository.AccountRepository;
import com.tickle_moa.backend.exception.BusinessException;
import com.tickle_moa.backend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountCommandService {

    private final AccountRepository accountRepository;

    public AccountCommandResponse createAccount(AccountCreateRequest request) {
        Account account = Account.builder()
                .userId(request.getUserId())
                .name(request.getName())
                .balance(request.getBalance())
                .build();

        Account savedAccount = accountRepository.save(account);

        return AccountCommandResponse.builder()
                .accountId(savedAccount.getAccountId())
                .message("계좌가 생성되었습니다.")
                .build();
    }

    public void deleteAccount(Long accountId) {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND));
        accountRepository.deleteById(accountId);
    }
}
