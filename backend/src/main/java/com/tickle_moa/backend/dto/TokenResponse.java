package com.tickle_moa.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {
    private String token;
    private Long userId;
    private String name;
    private String email;
    private String role;
}
