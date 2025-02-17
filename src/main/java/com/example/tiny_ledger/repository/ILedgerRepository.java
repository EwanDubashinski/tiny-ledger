package com.example.tiny_ledger.repository;

import com.example.tiny_ledger.model.Transaction;
import com.example.tiny_ledger.vo.Balance;

import java.util.List;

public interface ILedgerRepository {
    void setBalance(Balance newBalance);

    Balance getBalance();

    void addTransaction(Transaction transaction);

    List<Transaction> getAllTransactions();
}
