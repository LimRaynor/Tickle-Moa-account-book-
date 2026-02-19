package com.tickle_moa.backend.auth.repository;

import com.tickle_moa.backend.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUserId(Long userId);

    Optional<RefreshToken> findByToken(String token);

    void deleteByUserId(Long userId);
}
