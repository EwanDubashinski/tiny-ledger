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
@Getter
@Setter
public class Transaction {
    public enum Type {
        DEPOSIT, WITHDRAWAL
    }

    private Type type;
    private Amount amount;
    private OffsetDateTime timestamp;
    private TransactionId id;
}
