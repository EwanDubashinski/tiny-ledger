package com.example.tiny_ledger.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@AllArgsConstructor
public class Balance {
    @Getter
    private final BigDecimal balance;

    public Balance() {
        balance = BigDecimal.ZERO;
    }

    public Balance add(Amount amount) {
        return new Balance(this.balance.add(amount.getAmount()));
    }

    public Balance subtract(Amount amount) {
        if (amount.getAmount().compareTo(this.balance) > 0) throw new IllegalArgumentException();
        return new Balance(this.balance.subtract(amount.getAmount()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Balance that = (Balance) obj;
        return balance.equals(that.balance);
    }

    @Override
    public String toString() {
        return balance.toString();
    }
}
