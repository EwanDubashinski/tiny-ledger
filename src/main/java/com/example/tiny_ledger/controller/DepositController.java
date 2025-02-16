package com.example.tiny_ledger.controller;

import com.example.tiny_ledger.api.DepositApi;
import com.example.tiny_ledger.model.TransactionRequest;
import com.example.tiny_ledger.model.TransactionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepositController implements DepositApi {
    @Override
    public ResponseEntity<TransactionResponse> createDeposit(TransactionRequest transactionRequest) {
        return DepositApi.super.createDeposit(transactionRequest);
    }
}
