package com.tickle_moa.backend.account.command.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Getter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    public Account(Long userId, String name, BigDecimal balance) {
        this.userId = userId;
        this.name = name;
        this.balance = balance;
        this.createdAt = LocalDateTime.now();
    }
}
