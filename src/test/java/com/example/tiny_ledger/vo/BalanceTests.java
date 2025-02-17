package com.example.tiny_ledger.vo;

import com.example.tiny_ledger.exception.InsufficientFundsException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BalanceTests {
    @Test
    void defaultConstructor_ShouldSetBalanceToZero() {
        Balance balance = new Balance();
        assertEquals(BigDecimal.ZERO, balance.getBalance(), "Default balance should be zero.");
    }

    @Test
    void add_ShouldIncreaseBalanceByAmount() {
        Balance balance = new Balance(BigDecimal.valueOf(100));
        Amount amountToAdd = new Amount("50");
        Balance updatedBalance = balance.add(amountToAdd);

        assertEquals(BigDecimal.valueOf(150), updatedBalance.getBalance(), "Balance should increase by the added amount.");
    }

    @Test
    void subtract_ShouldDecreaseBalanceByAmount() {
        Balance balance = new Balance(BigDecimal.valueOf(100));
        Amount amountToSubtract = new Amount("30");
        Balance updatedBalance = balance.subtract(amountToSubtract);

        assertEquals(BigDecimal.valueOf(70), updatedBalance.getBalance(), "Balance should decrease by the subtracted amount.");
    }

    @Test
    void subtract_WhenAmountExceedsBalance_ShouldThrowInsufficientFundsException() {
        Balance balance = new Balance(BigDecimal.valueOf(100));
        Amount amountToSubtract = new Amount("150");

        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class,
                () -> balance.subtract(amountToSubtract),
                "Subtracting an amount larger than the balance should throw an InsufficientFundsException."
        );

        String expectedMessage = String.format(
                "The withdrawal amount %s is bigger than balance %s",
                amountToSubtract,
                balance
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void toString_ShouldReturnFormattedBalance() {
        Balance balance = new Balance(BigDecimal.valueOf(1234.56));
        String expectedFormattedBalance = "1234.56";

        assertEquals(expectedFormattedBalance, balance.toString(), "The formatted balance string is not as expected.");
    }
}
