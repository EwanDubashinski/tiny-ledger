package com.example.tiny_ledger.repository;

import com.example.tiny_ledger.model.Transaction;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryLedgerRepository implements ILedgerRepository {
    private BigDecimal balance = BigDecimal.ZERO;
    private final List<Transaction> transactions = new ArrayList<>();
    private final Object lock = new Object();

    public void updateBalance(BigDecimal newBalance) {
        synchronized (lock) {
            balance = newBalance;
        }
    }

    public BigDecimal getBalance() {
        synchronized (lock) {
            return balance;
        }
    }

    public void addTransaction(Transaction transaction) {
        synchronized (lock) {
            transactions.add(transaction);
        }
    }

    public List<Transaction> getAllTransactions() {
        synchronized (lock) {
            return new ArrayList<>(transactions);
        }
    }
}
