package com.tickle_moa.backend.account.query.controller;

import com.tickle_moa.backend.account.query.dto.response.AccountListResponse;
import com.tickle_moa.backend.account.query.service.AccountQueryService;
import com.tickle_moa.backend.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountQueryController {

    private final AccountQueryService accountQueryService;

    @GetMapping
    public ResponseEntity<ApiResponse<AccountListResponse>> getAccounts(@RequestParam Long userId) {
        AccountListResponse response = accountQueryService.getAccountsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
