package com.tickle_moa.backend.transaction.command.repository;

import com.tickle_moa.backend.transaction.command.entity.Transaction;

import java.util.Optional;

public interface TransactionRepository {

    Transaction save(Transaction transaction);

    Optional<Transaction> findById(Long tranId);

    void deleteById(Long tranId);
}
