package com.tickle_moa.backend.account.command.repository;

import com.tickle_moa.backend.account.command.entity.Account;

import java.util.Optional;

public interface AccountRepository {

    Account save(Account account);

    Optional<Account> findById(Long accountId);

    void deleteById(Long accountId);
}
