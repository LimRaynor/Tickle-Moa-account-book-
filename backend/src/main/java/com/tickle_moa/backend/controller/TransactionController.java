package com.tickle_moa.backend.controller;

import com.tickle_moa.backend.model.Transaction;
import com.tickle_moa.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
// 거래내역 CRUD 관련 API를 처리하는 컨트롤러
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // GET /api/transactions?accountId=1 요청이 오면 -> 해당 계좌 거래 목록 조회
    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions(@RequestParam Long accountId) {
        return ResponseEntity.ok(transactionService.getTransactionsByAccountId(accountId));
    }

    // POST /api/transactions 요청이 오면 -> 거래내역 생성
    @PostMapping
    public ResponseEntity<String> createTransaction(@RequestBody Transaction transaction) {
        transactionService.createTransaction(transaction);
        return ResponseEntity.ok("嫄곕옒 異붽? ?깃났");
    }

    // DELETE /api/transactions/{id} 요청이 오면 -> 해당 거래내역 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok("嫄곕옒 ??젣 ?깃났");
    }
}
