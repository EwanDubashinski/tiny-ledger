package com.example.tiny_ledger.controller;

import com.example.tiny_ledger.exception.InsufficientFundsException;
import com.example.tiny_ledger.exception.InvalidAmountException;
import com.example.tiny_ledger.model.Transaction;
import com.example.tiny_ledger.service.TinyLedgerService;
import com.example.tiny_ledger.vo.Amount;
import com.example.tiny_ledger.vo.Balance;
import com.example.tiny_ledger.vo.TransactionId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LedgerControllersIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TinyLedgerService service;

    @Test
    void getBalance_ReturnsBalanceResponse() throws Exception {
        Balance balance = new Balance(new BigDecimal("150.00"));
        when(service.getBalance()).thenReturn(balance);

        mockMvc.perform(get("/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance", is("150.00")));
    }

    @Test
    void createDeposit_ReturnsTransactionResponse() throws Exception {
        // Prepare a dummy transaction for a deposit.
        Transaction transaction = new Transaction(
                Transaction.Type.DEPOSIT,
                new Amount("100.00"),
                OffsetDateTime.now(),
                TransactionId.generate()
        );
        when(service.deposit("100.00")).thenReturn(transaction);

        String requestJson = "{\"amount\": \"100.00\"}";
        mockMvc.perform(post("/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(transaction.getId().getValue().toString())))
                .andExpect(jsonPath("$.amount", is("100.00")))
                .andExpect(jsonPath("$.type", is("deposit")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    void createWithdrawal_ReturnsTransactionResponse() throws Exception {
        // Prepare a dummy transaction for a withdrawal.
        Transaction transaction = new Transaction(
                Transaction.Type.WITHDRAWAL,
                new Amount("50.00"),
                OffsetDateTime.now(),
                TransactionId.generate()
        );
        when(service.withdrawal("50.00")).thenReturn(transaction);

        String requestJson = "{\"amount\": \"50.00\"}";
        mockMvc.perform(post("/withdrawal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(transaction.getId().getValue().toString())))
                .andExpect(jsonPath("$.amount", is("50.00")))
                .andExpect(jsonPath("$.type", is("withdrawal")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    void getHistory_ReturnsTransactionHistory() throws Exception {
        Transaction transaction1 = new Transaction(
                Transaction.Type.DEPOSIT,
                new Amount("100.00"),
                OffsetDateTime.now(),
                TransactionId.generate()
        );
        Transaction transaction2 = new Transaction(
                Transaction.Type.WITHDRAWAL,
                new Amount("30.00"),
                OffsetDateTime.now(),
                TransactionId.generate()
        );
        List<Transaction> transactions = List.of(transaction1, transaction2);
        when(service.getHistory()).thenReturn(transactions);

        mockMvc.perform(get("/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(transaction1.getId().getValue().toString())))
                .andExpect(jsonPath("$[0].amount", is("100.00")))
                .andExpect(jsonPath("$[0].type", is("deposit")))
                .andExpect(jsonPath("$[1].id", is(transaction2.getId().getValue().toString())))
                .andExpect(jsonPath("$[1].amount", is("30.00")))
                .andExpect(jsonPath("$[1].type", is("withdrawal")));
    }

    @Test
    void insufficientFunds_ReturnsErrorResponse() throws Exception {
        // Simulate a withdrawal that triggers an InsufficientFundsException.
        when(service.withdrawal(anyString()))
                .thenThrow(new InsufficientFundsException("The withdrawal amount 200.00 is bigger than balance 150.00"));

        String requestJson = "{\"amount\": \"200.00\"}";
        mockMvc.perform(post("/withdrawal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", is("INSUFFICIENT_FUNDS")))
                .andExpect(jsonPath("$.message", is("The withdrawal amount 200.00 is bigger than balance 150.00")));
    }

    @Test
    void invalidAmount_ReturnsErrorResponse() throws Exception {
        // Simulate a deposit that triggers an InvalidAmountException.
        when(service.deposit(anyString()))
                .thenThrow(new InvalidAmountException("Invalid number"));

        String requestJson = "{\"amount\": \"abc\"}";
        mockMvc.perform(post("/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", is("INVALID_AMOUNT")))
                .andExpect(jsonPath("$.message", is("Invalid number")));
    }
}
