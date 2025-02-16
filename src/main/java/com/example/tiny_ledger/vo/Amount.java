package com.example.tiny_ledger.vo;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

public class Amount {
    @Getter
    private final BigDecimal amount;

    public Amount(String stringAmount)  {
        if (stringAmount == null) {
            throw new IllegalArgumentException(); // TODO
        }

        try {
            amount = new BigDecimal(stringAmount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(); // TODO
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Amount that = (Amount) obj;
        return amount.equals(that.amount);
    }

    @Override
    public String toString() {
        return amount.toString();
    }
}
