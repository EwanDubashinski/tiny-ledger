package com.example.tiny_ledger.vo;

import com.example.tiny_ledger.exception.InvalidAmountException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import static com.example.tiny_ledger.util.StringUtils.formatMoney;

@EqualsAndHashCode
public class Amount {
    @Getter
    private final BigDecimal amount;

    public Amount(String stringAmount)  {
        if (stringAmount == null) {
            throw new InvalidAmountException("The amount cannot be null");
        }

        try {
            amount = new BigDecimal(stringAmount);
        } catch (NumberFormatException e) {
            throw new InvalidAmountException("Invalid number");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("The amount cannot be equal or below zero");
        }
    }

    @Override
    public String toString() {
        return formatMoney(amount);
    }
}
