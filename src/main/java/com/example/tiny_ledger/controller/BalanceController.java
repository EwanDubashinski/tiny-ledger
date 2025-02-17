package com.example.tiny_ledger.controller;

import com.example.tiny_ledger.api.BalanceApi;
import com.example.tiny_ledger.dto.BalanceResponse;
import com.example.tiny_ledger.service.TinyLedgerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BalanceController implements BalanceApi {
    private final TinyLedgerService service;

    @Override
    public ResponseEntity<BalanceResponse> getBalance() {
        var balanceString = service.getBalance().toString();
        var balanceResponse = new BalanceResponse()
                .balance(balanceString);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(balanceResponse);
    }
}
