package com.tickle_moa.backend.user.command.service;

import com.tickle_moa.backend.exception.BusinessException;
import com.tickle_moa.backend.exception.ErrorCode;
import com.tickle_moa.backend.user.command.dto.UserCreateRequest;
import com.tickle_moa.backend.user.command.entity.User;
import com.tickle_moa.backend.user.command.entity.UserRole;
import com.tickle_moa.backend.user.command.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCommandService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Long signup(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .build();

        User savedUser = userRepository.save(user);
        return savedUser.getUserId();
    }
}
