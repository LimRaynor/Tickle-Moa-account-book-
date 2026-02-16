package com.ohgiraffers.backend.service;

import com.ohgiraffers.backend.mapper.UserMapper;
import com.ohgiraffers.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service  // Spring이 이 클래스를 Service로 관리
public class AuthService {

	@Autowired  // Spring이 UserMapper를 자동으로 주입
	private UserMapper userMapper;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// 회원가입
	public void signup(User user) {
		// 1. 이메일 중복 확인
		User existing = userMapper.findByEmail(user.getEmail());
		if (existing != null) {
			throw new RuntimeException("이미 존재하는 이메일입니다");
		}

		// 2. 비밀번호 암호화
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// 3. 기본 역할 설정
		user.setRole("USER");

		// 4. DB에 저장
		userMapper.insertUser(user);
	}

	// 이메일로 사용자 조회 (로그인 시 사용)
	public User findByEmail(String email) {
		return userMapper.findByEmail(email);
	}
}
