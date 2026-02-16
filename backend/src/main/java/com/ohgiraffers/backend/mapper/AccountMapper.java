package com.ohgiraffers.backend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ohgiraffers.backend.model.Account;

// MyBatis Mapper 인터페이스 - AccountMapper.xml과 연결됨
// 메서드: findByUserId, insertAccount, deleteById
@Mapper
public interface AccountMapper {
	// 특정 유저의 계좌 목록 조회
	List<Account> findByUserId(Long userId);

	// 계좌 생성
	void insertAccount(Account account);

	// 계좌 삭제
	void deleteById(Long accountId);
}
