package com.tickle_moa.backend.transaction.query.mapper;

import com.tickle_moa.backend.transaction.query.dto.response.TransactionDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TransactionMapper {

    List<TransactionDTO> findByAccountId(Long accountId);
}
