package com.tickle_moa.backend.transaction.query.controller;

import com.tickle_moa.backend.common.ApiResponse;
import com.tickle_moa.backend.transaction.query.dto.response.TransactionListResponse;
import com.tickle_moa.backend.transaction.query.service.TransactionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionQueryController {

    private final TransactionQueryService transactionQueryService;

    @GetMapping
    public ResponseEntity<ApiResponse<TransactionListResponse>> getTransactions(
            @RequestParam Long accountId) {
        TransactionListResponse response = transactionQueryService.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
