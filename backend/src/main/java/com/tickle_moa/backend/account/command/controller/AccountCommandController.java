package com.tickle_moa.backend.account.command.controller;

import com.tickle_moa.backend.account.command.dto.request.AccountCreateRequest;
import com.tickle_moa.backend.account.command.dto.response.AccountCommandResponse;
import com.tickle_moa.backend.account.command.service.AccountCommandService;
import com.tickle_moa.backend.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountCommandController {

    private final AccountCommandService accountCommandService;

    @PostMapping
    public ResponseEntity<ApiResponse<AccountCommandResponse>> createAccount(
            @RequestBody AccountCreateRequest request) {
        AccountCommandResponse response = accountCommandService.createAccount(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable Long id) {
        accountCommandService.deleteAccount(id);
        return ResponseEntity.ok(ApiResponse.success(null, "계좌가 삭제되었습니다."));
    }
}
