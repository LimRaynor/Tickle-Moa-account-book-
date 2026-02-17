package com.ohgiraffers.backend.controller;

import com.ohgiraffers.backend.model.User;
import com.ohgiraffers.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody User user) {
		authService.signup(user);
		return ResponseEntity.ok("회원가입 성공");
	}

	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User user) {
		User found = authService.findByEmail(user.getEmail());
		if (found == null) {
			return ResponseEntity.badRequest().build(); // 상태코드로 응답받음
			/*body*/ //("존재하지 않는 이메일입니다");
		}
	//	return ResponseEntity.ok("로그인 성공"); // 기존의 로그인성공 임의이메세지 -> 프론트엔드 조회
		return ResponseEntity.ok(found);  // User 객체 반환 (userId, name, email 포함)
	}
}