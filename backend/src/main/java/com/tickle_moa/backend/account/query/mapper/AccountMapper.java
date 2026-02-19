package com.tickle_moa.backend.account.query.mapper;

import com.tickle_moa.backend.account.query.dto.response.AccountDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccountMapper {

    List<AccountDTO> findByUserId(Long userId);
}
