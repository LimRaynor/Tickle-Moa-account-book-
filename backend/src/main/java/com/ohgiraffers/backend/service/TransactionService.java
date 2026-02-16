package com.ohgiraffers.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohgiraffers.backend.mapper.TransactionMapper;
import com.ohgiraffers.backend.model.Transaction;

// 거래 추가, 조회, 삭제 비즈니스 로직
// TransactionMapper를 주입받아서 DB 조회/저장
// 거래 추가 시 accounts 테이블의 balance도 업데이트
@Service
public class TransactionService {
	@Autowired
	private TransactionMapper transactionMapper;

	// 계좌거래 목록조회
	public List<Transaction> getTransactionsByAccountId(Long accountId) {
		return transactionMapper.findByAccountId(accountId);
	}

	// 거래 추가
	public void createTransaction(Transaction transaction) {
		transactionMapper.insertTransaction(transaction);
	}

	// 거래 삭제
	public void deleteTransaction(Long tranId) {
		transactionMapper.deleteById(tranId);
	}


}
