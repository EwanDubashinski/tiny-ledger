package com.example.tiny_ledger.vo;

import com.example.tiny_ledger.exception.InsufficientFundsException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

import static com.example.tiny_ledger.util.StringUtils.formatMoney;

@AllArgsConstructor
@EqualsAndHashCode
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
        if (amount.getAmount().compareTo(this.balance) > 0) {
            throw new InsufficientFundsException(
                    String.format(
                            "The withdrawal amount %s is bigger than balance %s",
                            amount,
                            this
                    )
            );
        }
        return new Balance(this.balance.subtract(amount.getAmount()));
    }

    @Override
    public String toString() {
        return formatMoney(balance);
    }
}
