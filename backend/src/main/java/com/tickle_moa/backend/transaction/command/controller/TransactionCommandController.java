package com.tickle_moa.backend.transaction.command.controller;

import com.tickle_moa.backend.common.ApiResponse;
import com.tickle_moa.backend.transaction.command.dto.request.TransactionCreateRequest;
import com.tickle_moa.backend.transaction.command.dto.response.TransactionCommandResponse;
import com.tickle_moa.backend.transaction.command.service.TransactionCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionCommandController {

    private final TransactionCommandService transactionCommandService;

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionCommandResponse>> createTransaction(
            @RequestBody TransactionCreateRequest request) {
        TransactionCommandResponse response = transactionCommandService.createTransaction(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTransaction(@PathVariable Long id) {
        transactionCommandService.deleteTransaction(id);
        return ResponseEntity.ok(ApiResponse.success(null, "거래내역이 삭제되었습니다."));
    }
}
