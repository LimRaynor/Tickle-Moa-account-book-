package com.tickle_moa.backend.controller;

import com.tickle_moa.backend.dto.LoginRequest;
import com.tickle_moa.backend.dto.SignupRequest;
import com.tickle_moa.backend.dto.TokenResponse;
import com.tickle_moa.backend.model.User;
import com.tickle_moa.backend.security.JwtTokenProvider;
import com.tickle_moa.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        authService.signup(user);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        User user = authService.login(request);
        String token = jwtTokenProvider.generateToken(user);
        TokenResponse response = new TokenResponse(
                token,
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
        return ResponseEntity.ok(response);
    }
}
