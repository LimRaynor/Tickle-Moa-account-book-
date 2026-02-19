package com.tickle_moa.backend.transaction.command.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tran_id")
    private Long tranId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private String type;

    private String category;

    @Column(nullable = false)
    private BigDecimal amount;

    private String description;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    public Transaction(Long accountId, String type, String category,
                       BigDecimal amount, String description, LocalDate date) {
        this.accountId = accountId;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.createdAt = LocalDateTime.now();
    }
}
