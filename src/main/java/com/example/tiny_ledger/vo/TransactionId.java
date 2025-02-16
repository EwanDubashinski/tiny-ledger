package com.example.tiny_ledger.vo;

import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

public final class TransactionId {
    @Getter
    private final UUID value;

    // Private constructor to enforce factory method usage
    private TransactionId(UUID value) {
        this.value = Objects.requireNonNull(value, "Transaction ID cannot be null");
    }

    // Factory method for creating a new ID
    public static TransactionId generate() {
        return new TransactionId(UUID.randomUUID());
    }

    // Factory method for reconstructing from an existing ID (e.g., from a database)
    public static TransactionId from(String id) {
        return new TransactionId(UUID.fromString(id));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TransactionId that = (TransactionId) obj;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
