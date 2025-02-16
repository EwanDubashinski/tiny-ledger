package com.example.tiny_ledger.controller;

import com.example.tiny_ledger.api.WithdrawalApi;
import com.example.tiny_ledger.model.TransactionRequest;
import com.example.tiny_ledger.model.TransactionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WithdrawalController implements WithdrawalApi {
    @Override
    public ResponseEntity<TransactionResponse> createWithdrawal(TransactionRequest transactionRequest) {
        return WithdrawalApi.super.createWithdrawal(transactionRequest);
    }
}
