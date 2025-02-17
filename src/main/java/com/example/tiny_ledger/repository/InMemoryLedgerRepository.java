package com.example.tiny_ledger.repository;

import com.example.tiny_ledger.model.Transaction;
import com.example.tiny_ledger.vo.Balance;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryLedgerRepository implements ILedgerRepository {
    @Getter
    @Setter
    private Balance balance = new Balance();
    private final List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }
}
