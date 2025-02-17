package com.example.tiny_ledger.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilsTests {
    @Test
    void formatMoney_WithWholeNumber_ShouldReturnTwoDecimalPlaces() {
        BigDecimal amount = new BigDecimal("100");
        String formatted = StringUtils.formatMoney(amount);
        assertEquals("100.00", formatted, "Whole number should be formatted with two decimals");
    }

    @Test
    void formatMoney_WithOneDecimal_ShouldReturnOneDecimalPaddedToTwoDecimals() {
        BigDecimal amount = new BigDecimal("123.4");
        String formatted = StringUtils.formatMoney(amount);
        assertEquals("123.40", formatted, "Number with one decimal should be padded to two decimals");
    }

    @Test
    void formatMoney_WithTwoDecimals_ShouldReturnExactValue() {
        BigDecimal amount = new BigDecimal("1234.56");
        String formatted = StringUtils.formatMoney(amount);
        assertEquals("1234.56", formatted, "Number with two decimals should be formatted correctly");
    }

    @Test
    void formatMoney_WithMoreThanTwoDecimals_ShouldNotRoundExtraDecimals() {
        BigDecimal amount = new BigDecimal("1234.5678");
        String formatted = StringUtils.formatMoney(amount);
        assertEquals("1234.5678", formatted, "Number with more than two decimals should display all decimals without rounding");
    }

    @Test
    void formatMoney_WithNegativeValue_ShouldFormatCorrectly() {
        BigDecimal amount = new BigDecimal("-987.65");
        String formatted = StringUtils.formatMoney(amount);
        assertEquals("-987.65", formatted, "Negative number should be formatted correctly");
    }

    @Test
    void formatMoney_WithZero_ShouldReturnZeroWithTwoDecimals() {
        BigDecimal amount = BigDecimal.ZERO;
        String formatted = StringUtils.formatMoney(amount);
        assertEquals("0.00", formatted, "Zero should be formatted as 0.00");
    }
}
