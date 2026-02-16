package com.ohgiraffers.backend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ohgiraffers.backend.model.Transaction;

// MyBatis Mapper 인터페이스 - TransactionMapper.xml과 연결됨
// 메서드: findByAccountId, insertTransaction, deleteById
@Mapper
public interface TransactionMapper {
	List<Transaction> findByAccountId(Long accountId);

	void insertTransaction(Transaction transaction);

	void deleteById(Long tranId);
}
