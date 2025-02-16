package com.example.tiny_ledger.model;

import com.example.tiny_ledger.vo.Amount;
import com.example.tiny_ledger.vo.TransactionId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    public enum Type {
        DEPOSIT, WITHDRAWAL
    }

    @Getter
    @Setter
    private Type type;

    @Getter
    @Setter
    private Amount amount;

    @Getter
    @Setter
    private OffsetDateTime timestamp;

    @Getter
    @Setter
    private TransactionId id;
}
