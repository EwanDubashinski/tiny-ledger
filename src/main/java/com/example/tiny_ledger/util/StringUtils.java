package com.example.tiny_ledger.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class StringUtils {
    public static String formatMoney(BigDecimal amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("0.00################", symbols);
        return df.format(amount);
    }
}
