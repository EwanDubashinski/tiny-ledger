package com.example.tiny_ledger.controller;

import com.example.tiny_ledger.api.WithdrawalApi;
import com.example.tiny_ledger.dto.TransactionRequest;
import com.example.tiny_ledger.dto.TransactionResponse;
import com.example.tiny_ledger.service.TinyLedgerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WithdrawalController implements WithdrawalApi {
    private final TinyLedgerService service;
    @Override
    public ResponseEntity<TransactionResponse> createWithdrawal(TransactionRequest transactionRequest) {
        var transaction = service.withdrawal(transactionRequest.getAmount());
        var response = new TransactionResponse()
                .id(transaction.getId().getValue())
                .amount(transaction.getAmount().toString())
                .type(TransactionResponse.TypeEnum.WITHDRAWAL)
                .timestamp(transaction.getTimestamp());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
