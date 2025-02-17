package com.example.tiny_ledger.service;

import com.example.tiny_ledger.model.Transaction;
import com.example.tiny_ledger.repository.ILedgerRepository;
import com.example.tiny_ledger.vo.Amount;
import com.example.tiny_ledger.vo.Balance;
import com.example.tiny_ledger.vo.TransactionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TinyLedgerServiceTests {

    @Mock
    private ILedgerRepository repository;

    @InjectMocks
    private TinyLedgerService service;

    private Balance initialBalance;

    @BeforeEach
    void setUp() {
        // Set up an initial balance of 100.00 for deposit and withdrawal tests.
        initialBalance = new Balance(new BigDecimal("100.00"));
    }

    @Test
    void getHistory_ShouldReturnAllTransactions() {
        // Arrange: create a dummy transaction list.
        Transaction dummyTransaction = new Transaction(
                Transaction.Type.DEPOSIT,
                new Amount("100.00"),
                OffsetDateTime.now(),
                TransactionId.generate()
        );
        List<Transaction> transactions = List.of(dummyTransaction);
        when(repository.getAllTransactions()).thenReturn(transactions);

        // Act:
        List<Transaction> result = service.getHistory();

        // Assert:
        assertEquals(transactions, result, "Service should return the transaction history from the repository.");
        verify(repository).getAllTransactions();
    }

    @Test
    void getBalance_ShouldReturnCurrentBalance() {
        // Arrange:
        when(repository.getBalance()).thenReturn(initialBalance);

        // Act:
        Balance result = service.getBalance();

        // Assert:
        assertEquals(initialBalance, result, "Service should return the current balance from the repository.");
        verify(repository).getBalance();
    }

    @Test
    void deposit_ShouldIncreaseBalanceAndRecordTransaction() {
        // Arrange:
        String depositAmount = "50.00";
        // Repository returns the current balance
        when(repository.getBalance()).thenReturn(initialBalance);

        // Act:
        Transaction transaction = service.deposit(depositAmount);

        // The expected new balance should be initial balance + deposit amount.
        Balance expectedNewBalance = initialBalance.add(new Amount(depositAmount));

        // Assert:
        // Verify that repository.setBalance was called with the expected new balance.
        verify(repository).setBalance(expectedNewBalance);

        // Capture the transaction passed to addTransaction for further verification.
        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(repository).addTransaction(transactionCaptor.capture());
        Transaction capturedTransaction = transactionCaptor.getValue();

        // Verify that the captured transaction matches the one returned.
        assertEquals(capturedTransaction, transaction, "The returned transaction should match the recorded transaction.");

        // Verify that the transaction type is DEPOSIT.
        assertEquals(Transaction.Type.DEPOSIT, transaction.getType(), "Transaction type should be DEPOSIT.");

        // Verify that the transaction amount is as expected.
        assertEquals(new Amount(depositAmount), transaction.getAmount(), "Transaction amount should match the deposit amount.");
    }

    @Test
    void withdrawal_ShouldDecreaseBalanceAndRecordTransaction() {
        // Arrange:
        String withdrawalAmount = "30.00";
        // Repository returns the current balance
        when(repository.getBalance()).thenReturn(initialBalance);

        // Act:
        Transaction transaction = service.withdrawal(withdrawalAmount);

        // The expected new balance should be initial balance - withdrawal amount.
        Balance expectedNewBalance = initialBalance.subtract(new Amount(withdrawalAmount));

        // Assert:
        // Verify that repository.setBalance was called with the expected new balance.
        verify(repository).setBalance(expectedNewBalance);

        // Capture the transaction passed to addTransaction for further verification.
        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(repository).addTransaction(transactionCaptor.capture());
        Transaction capturedTransaction = transactionCaptor.getValue();

        // Verify that the captured transaction matches the one returned.
        assertEquals(capturedTransaction, transaction, "The returned transaction should match the recorded transaction.");

        // Verify that the transaction type is WITHDRAWAL.
        assertEquals(Transaction.Type.WITHDRAWAL, transaction.getType(), "Transaction type should be WITHDRAWAL.");

        // Verify that the transaction amount is as expected.
        assertEquals(new Amount(withdrawalAmount), transaction.getAmount(), "Transaction amount should match the withdrawal amount.");
    }
}
