package com.tickle_moa.backend.transaction.command.service;

import com.tickle_moa.backend.exception.BusinessException;
import com.tickle_moa.backend.exception.ErrorCode;
import com.tickle_moa.backend.transaction.command.dto.request.TransactionCreateRequest;
import com.tickle_moa.backend.transaction.command.dto.response.TransactionCommandResponse;
import com.tickle_moa.backend.transaction.command.entity.Transaction;
import com.tickle_moa.backend.transaction.command.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionCommandService {

    private final TransactionRepository transactionRepository;

    public TransactionCommandResponse createTransaction(TransactionCreateRequest request) {
        Transaction transaction = Transaction.builder()
                .accountId(request.getAccountId())
                .type(request.getType())
                .category(request.getCategory())
                .amount(request.getAmount())
                .description(request.getDescription())
                .date(request.getDate())
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionCommandResponse.builder()
                .tranId(savedTransaction.getTranId())
                .message("거래내역이 생성되었습니다.")
                .build();
    }

    public void deleteTransaction(Long tranId) {
        transactionRepository.findById(tranId)
                .orElseThrow(() -> new BusinessException(ErrorCode.TRANSACTION_NOT_FOUND));
        transactionRepository.deleteById(tranId);
    }
}
