package com.example.tiny_ledger.vo;

import com.example.tiny_ledger.exception.InvalidAmountException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AmountTests {
    @Test
    void givenNullString_whenConstructingAmount_thenThrowInvalidAmountException() {
        InvalidAmountException exception = assertThrows(InvalidAmountException.class, () -> new Amount(null));
        assertEquals("The amount cannot be null", exception.getMessage());
    }

    @Test
    void givenInvalidNumberString_whenConstructingAmount_thenThrowInvalidAmountException() {
        String invalidNumber = "abc123";
        InvalidAmountException exception = assertThrows(InvalidAmountException.class, () -> new Amount(invalidNumber));
        assertEquals("Invalid number", exception.getMessage());
    }

    @Test
    void givenZeroString_whenConstructingAmount_thenThrowInvalidAmountException() {
        String zeroAmount = "0";
        InvalidAmountException exception = assertThrows(InvalidAmountException.class, () -> new Amount(zeroAmount));
        assertEquals("The amount cannot be equal or below zero", exception.getMessage());
    }

    @Test
    void givenNegativeString_whenConstructingAmount_thenThrowInvalidAmountException() {
        String negativeAmount = "-10";
        InvalidAmountException exception = assertThrows(InvalidAmountException.class, () -> new Amount(negativeAmount));
        assertEquals("The amount cannot be equal or below zero", exception.getMessage());
    }

    @Test
    void givenValidAmountString_whenConstructingAmount_thenReturnCorrectAmount() {
        String validAmount = "1234.56";
        Amount amount = new Amount(validAmount);
        assertEquals(new BigDecimal(validAmount), amount.getAmount());
    }

    @Test
    void givenValidAmountString_whenToString_thenReturnFormattedMoney() {
        String validAmount = "1000";
        Amount amount = new Amount(validAmount);
        String expectedFormattedValue = "1000.00";
        assertEquals(expectedFormattedValue, amount.toString());
    }
}
