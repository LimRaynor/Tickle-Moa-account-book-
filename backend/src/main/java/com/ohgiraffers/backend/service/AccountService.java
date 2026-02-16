
package com.ohgiraffers.backend.service;

import com.ohgiraffers.backend.mapper.AccountMapper;
import com.ohgiraffers.backend.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AccountService {

	@Autowired
	private AccountMapper accountMapper;

	// 특정 유저의 계좌 목록 조회
	// 공개된 계좌목록 이름 (타입 이름)
	// 반환 DB인텁페이스
	public List<Account> getAccountsByUserId(Long userId) {
		return accountMapper.findByUserId(userId);
	}

	// 계좌 생성
	public void createAccount(Account account) {
		accountMapper.insertAccount(account);
	}

	// 계좌 삭제
	public void deleteAccount(Long accountId) {
		accountMapper.deleteById(accountId);
	}
}
