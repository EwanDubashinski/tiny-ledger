package com.example.tiny_ledger.repository;

import com.example.tiny_ledger.model.Transaction;
import com.example.tiny_ledger.vo.Amount;
import com.example.tiny_ledger.vo.Balance;
import com.example.tiny_ledger.vo.TransactionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryLedgerRepositoryTests {

    private InMemoryLedgerRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryLedgerRepository();
    }

    @Test
    void defaultBalance_ShouldBeZero() {
        Balance balance = repository.getBalance();
        assertEquals("0.00", balance.toString(), "Default balance should be zero (formatted as 0.00)");
    }

    @Test
    void setBalance_ShouldUpdateBalance() {
        Balance newBalance = new Balance(new java.math.BigDecimal("250.00"));
        repository.setBalance(newBalance);
        assertEquals(newBalance, repository.getBalance(), "The balance should be updated correctly via setter.");
    }

    @Test
    void addTransaction_ShouldStoreTransaction() {
        Transaction transaction = createDummyTransaction("100.00");
        repository.addTransaction(transaction);

        List<Transaction> transactions = repository.getAllTransactions();
        assertEquals(1, transactions.size(), "Repository should contain one transaction after adding one.");
        assertEquals(transaction, transactions.get(0), "The stored transaction should match the added transaction.");
    }

    @Test
    void getAllTransactions_ShouldReturnCopyOfTransactions() {
        Transaction transaction = createDummyTransaction("50.00");
        repository.addTransaction(transaction);

        // Get a copy of the transactions and modify the copy.
        List<Transaction> transactionsCopy = repository.getAllTransactions();
        transactionsCopy.clear();

        // Get the transactions from repository again.
        List<Transaction> transactionsAfterClear = repository.getAllTransactions();
        assertEquals(1, transactionsAfterClear.size(),
                "Clearing the returned copy should not affect the internal repository transactions.");
    }

    /**
     * Helper method to create a dummy Transaction with a specified amount and type.
     */
    private Transaction createDummyTransaction(String amountStr) {
        return new Transaction(
                Transaction.Type.DEPOSIT,
                new Amount(amountStr),
                OffsetDateTime.now(),
                TransactionId.generate()
        );
    }
}
