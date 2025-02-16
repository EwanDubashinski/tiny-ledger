package com.example.tiny_ledger.controller;

import com.example.tiny_ledger.api.HistoryApi;
import com.example.tiny_ledger.model.Transaction;
import com.example.tiny_ledger.model.TransactionResponse;
import com.example.tiny_ledger.service.TinyLedgerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HistoryController implements HistoryApi {
    private TinyLedgerService service;
    @Override
    public ResponseEntity<List<TransactionResponse>> getTransactionHistory() {
        var body = service
                .getHistory()
                .stream()
                .map(transaction -> new TransactionResponse()
                        .id(transaction.getId().getValue())
                        .amount(transaction.getAmount().toString())
                        .type(transaction.getType() == Transaction.Type.DEPOSIT ?
                                TransactionResponse.TypeEnum.DEPOSIT :
                                TransactionResponse.TypeEnum.WITHDRAWAL)
                )
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }
}
