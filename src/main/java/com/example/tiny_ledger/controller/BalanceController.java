package com.example.tiny_ledger.controller;

import com.example.tiny_ledger.api.BalanceApi;
import com.example.tiny_ledger.model.BalanceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController implements BalanceApi {
    @Override
    public ResponseEntity<BalanceResponse> getBalance() {
        return BalanceApi.super.getBalance();
    }
}
