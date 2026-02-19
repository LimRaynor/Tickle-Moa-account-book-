package com.tickle_moa.backend.auth.service;

import com.tickle_moa.backend.auth.dto.LoginRequest;
import com.tickle_moa.backend.auth.dto.TokenResponse;
import com.tickle_moa.backend.auth.entity.RefreshToken;
import com.tickle_moa.backend.auth.repository.AuthRepository;
import com.tickle_moa.backend.exception.BusinessException;
import com.tickle_moa.backend.exception.ErrorCode;
import com.tickle_moa.backend.jwt.JwtTokenProvider;
import com.tickle_moa.backend.user.command.entity.User;
import com.tickle_moa.backend.user.command.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtTokenProvider.createToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);

        // RefreshToken 저장 또는 갱신
        Optional<RefreshToken> existingToken = authRepository.findByUserId(user.getUserId());
        if (existingToken.isPresent()) {
            existingToken.get().updateToken(refreshToken, LocalDateTime.now().plusDays(7));
        } else {
            authRepository.save(RefreshToken.builder()
                    .userId(user.getUserId())
                    .token(refreshToken)
                    .expiryDate(LocalDateTime.now().plusDays(7))
                    .build());
        }

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
