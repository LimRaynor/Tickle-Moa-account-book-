package com.tickle_moa.backend.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
// erd보고 넣기
// bigint==Long
public class User {

	private Long userId;
	private String name;
	private String email;
	private String password;
	private LocalDateTime createdAt;
	private String role;

}
