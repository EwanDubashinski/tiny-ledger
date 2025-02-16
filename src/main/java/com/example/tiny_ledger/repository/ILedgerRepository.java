package com.example.tiny_ledger.repository;

import com.example.tiny_ledger.model.Transaction;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

public interface ILedgerRepository {
    void updateBalance(BigDecimal newBalance);

    BigDecimal getBalance();

    void addTransaction(Transaction transaction);

    List<Transaction> getAllTransactions();
}
