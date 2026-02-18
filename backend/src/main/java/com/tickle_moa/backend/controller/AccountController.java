package com.tickle_moa.backend.controller;

import com.tickle_moa.backend.model.Account;
import com.tickle_moa.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
// 계좌 CRUD 관련 API를 처리하는 컨트롤러
public class AccountController {

    @Autowired
    private AccountService accountService;

    // GET /api/accounts?userId=1 요청이 오면 -> 해당 사용자 계좌 목록 조회
    @GetMapping
    public ResponseEntity<List<Account>> getAccounts(@RequestParam Long userId) {
        return ResponseEntity.ok(accountService.getAccountsByUserId(userId));
    }

    // POST /api/accounts 요청이 오면 -> 계좌 생성
    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody Account account) {
        accountService.createAccount(account);
        return ResponseEntity.ok("계좌가 생성되었습니다.");
    }

    // DELETE /api/accounts/{id} 요청이 오면 -> 해당 계좌 삭제
    @DeleteMapping("/{id}") // {id}==id에 어떤값이 올지 모르는 그값을 변수로설정해 어떤값이라도 진행
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok("계좌가 삭제되었습니다.");
    }
}
