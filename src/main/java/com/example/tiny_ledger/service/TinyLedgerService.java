package com.example.tiny_ledger.service;

import com.example.tiny_ledger.model.Transaction;
import com.example.tiny_ledger.repository.ILedgerRepository;
import org.springframework.stereotype.Service;

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
}
