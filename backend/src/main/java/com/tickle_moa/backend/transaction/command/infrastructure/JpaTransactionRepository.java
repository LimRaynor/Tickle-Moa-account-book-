package com.tickle_moa.backend.transaction.command.infrastructure;

import com.tickle_moa.backend.transaction.command.entity.Transaction;
import com.tickle_moa.backend.transaction.command.repository.TransactionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTransactionRepository extends JpaRepository<Transaction, Long>, TransactionRepository {
}
