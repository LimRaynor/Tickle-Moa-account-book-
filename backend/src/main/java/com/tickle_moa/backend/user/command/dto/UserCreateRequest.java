package com.tickle_moa.backend.user.command.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCreateRequest {

    private final String name;
    private final String email;
    private final String password;
}
