package com.tickle_moa.backend.account.command.infrastructure;

import com.tickle_moa.backend.account.command.entity.Account;
import com.tickle_moa.backend.account.command.repository.AccountRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAccountRepository extends JpaRepository<Account, Long>, AccountRepository {
}
