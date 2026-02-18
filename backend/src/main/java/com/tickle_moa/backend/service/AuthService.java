package com.tickle_moa.backend.service;

import com.tickle_moa.backend.dto.LoginRequest;
import com.tickle_moa.backend.mapper.UserMapper;
import com.tickle_moa.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public void signup(User user) {
		User existing = userMapper.findByEmail(user.getEmail());
		if (existing != null) {
			throw new RuntimeException("이미 존재하는 이메일입니다");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("USER");
		userMapper.insertUser(user);
	}

	public User login(LoginRequest request) {
		User user = userMapper.findByEmail(request.getEmail());
		if (user == null) {
			throw new RuntimeException("존재하지 않는 이메일입니다");
		}
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다");
		}
		return user;
	}

	public User findByEmail(String email) {
		return userMapper.findByEmail(email);
	}
}
