package com.example.tiny_ledger.service;

import com.example.tiny_ledger.model.Transaction;
import com.example.tiny_ledger.repository.ILedgerRepository;
import com.example.tiny_ledger.vo.Amount;
import com.example.tiny_ledger.vo.Balance;
import com.example.tiny_ledger.vo.TransactionId;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class TinyLedgerService {
    private final ILedgerRepository repository;

    public TinyLedgerService(ILedgerRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> getHistory() {
        return repository.getAllTransactions();
    }

    public Balance getBalance() {
        return repository.getBalance();
    }

    private Transaction executeTransaction(Transaction.Type type, String stringAmount) {
        Amount amount = new Amount(stringAmount);

        Balance newBalance;
        if (type == Transaction.Type.DEPOSIT) {
            newBalance = repository.getBalance().add(amount);
        } else {
            newBalance = repository.getBalance().subtract(amount);
        }
        repository.setBalance(newBalance);

        Transaction transaction = new Transaction(
                type,
                amount,
                OffsetDateTime.now(),
                TransactionId.generate()
        );
        repository.addTransaction(transaction);
        return transaction;
    }
    public Transaction deposit(String stringAmount) {
        return executeTransaction(Transaction.Type.DEPOSIT, stringAmount);
    }

    public Transaction withdrawal(String stringAmount) {
        return executeTransaction(Transaction.Type.WITHDRAWAL, stringAmount);
    }
}
