package com.tickle_moa.backend.controller;

import com.tickle_moa.backend.model.Transaction;
import com.tickle_moa.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 프론트엔드와 데이터 주고받기
@RequestMapping("/api/transactions") // 여기 컨트롤러 기본경로
// 거래내역 CRUD 관련 API를 처리하는 컨트롤러
public class TransactionController {

    @Autowired // 자동의존성주입 -> 서비스객체이용가능
    private TransactionService transactionService;

    // GET방식으로 /api/transactions?accountId=1 요청이 오면 -> 해당 계좌 거래 목록 조회
    @GetMapping // 조회 방식
	// 조회 방식으로
    public ResponseEntity<List<Transaction>> getTransactions(@RequestParam Long accountId) {
// 공용 접근제어자로 ResponseEntity에담아 거래내역목록을 조회하겠다. 거래목록조회 @외부에서 보네준 계정Id를
        return ResponseEntity.ok(transactionService.getTransactionsByAccountId(accountId));
    }
    // POST방식으로 /api/transactions 요청이 오면 -> 거래내역 생성
    @PostMapping // 추가 방식
    public ResponseEntity<String> createTransaction(@RequestBody Transaction transaction) {
		// 공용 호출로 createTransaction이 요청오면 ResponseEntity에 담아 응답하겠다
        transactionService.createTransaction(transaction);
        return ResponseEntity.ok("거래내역이 생성되었습니다.");
    }

    // DELETE방식으로 /api/transactions/{id} 요청이 오면 -> 해당 거래내역 삭제
    @DeleteMapping("/{id}")
    // ResponsEntity에 담아 http 상태코드와 함께 응답
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok("거래내역이 삭제되었습니다.");
    }
}
