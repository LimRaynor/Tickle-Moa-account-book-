package com.ohgiraffers.backend.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
// users 테이블과 매핑되는 VO 클래스
// 필드: userId, name, email, password, createdAt, role
public class User {

	private Long userId;
	private String name;
	private String eamil;
	private String password;
	private LocalDateTime createdAt;
	private String role;

}
