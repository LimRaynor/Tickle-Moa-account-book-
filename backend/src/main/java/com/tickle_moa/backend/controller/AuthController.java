package com.tickle_moa.backend.controller;

import com.tickle_moa.backend.model.User;
import com.tickle_moa.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
// 인증(회원가입/로그인) 관련 API를 처리하는 컨트롤러
public class AuthController {

    @Autowired
    private AuthService authService;

    // POST /api/auth/signup 요청이 오면 -> 회원가입 처리
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        authService.signup(user);
        return ResponseEntity.ok("?뚯썝媛???깃났");
    }

    // POST /api/auth/login 요청이 오면 -> 이메일 기준으로 사용자 조회 후 반환
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User found = authService.findByEmail(user.getEmail());
        if (found == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(found);
    }
}
