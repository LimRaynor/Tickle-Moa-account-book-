package com.tickle_moa.backend.transaction.query.service;

import com.tickle_moa.backend.transaction.query.dto.response.TransactionDTO;
import com.tickle_moa.backend.transaction.query.dto.response.TransactionListResponse;
import com.tickle_moa.backend.transaction.query.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionQueryService {

    private final TransactionMapper transactionMapper;

    public TransactionListResponse getTransactionsByAccountId(Long accountId) {
        List<TransactionDTO> transactions = transactionMapper.findByAccountId(accountId);
        return TransactionListResponse.builder()
                .transactions(transactions)
                .totalCount(transactions.size())
                .build();
    }
}
