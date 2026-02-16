package com.ohgiraffers.backend.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ohgiraffers.backend.model.User;

// MyBatis Mapper 인터페이스 - UserMapper.xml과 연결됨
// 메서드: findByEmail, insertUser, findByUserId
@Mapper // DB연결 자동화 (인터페이스랑 DB 쿼리(질문)랑 연결)
public interface UserMapper {
	User findByEmail(String email);

	void insertUser(User user);
}
