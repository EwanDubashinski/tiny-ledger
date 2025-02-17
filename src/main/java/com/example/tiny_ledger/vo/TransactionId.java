package com.example.tiny_ledger.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;

@EqualsAndHashCode
@ToString
public final class TransactionId {
    @Getter
    private final UUID value;

    // Private constructor to enforce factory method usage
    private TransactionId(UUID value) {
        this.value = Objects.requireNonNull(value, "Transaction ID cannot be null");
    }

    public static TransactionId generate() {
        return new TransactionId(UUID.randomUUID());
    }
}
