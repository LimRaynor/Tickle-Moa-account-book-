package com.tickle_moa.backend.user.command.controller;

import com.tickle_moa.backend.common.ApiResponse;
import com.tickle_moa.backend.user.command.dto.UserCreateRequest;
import com.tickle_moa.backend.user.command.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserCommandController {

    private final UserCommandService userCommandService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Long>> signup(@RequestBody UserCreateRequest request) {
        Long userId = userCommandService.signup(request);
        return ResponseEntity.ok(ApiResponse.success(userId, "회원가입 성공"));
    }
}
