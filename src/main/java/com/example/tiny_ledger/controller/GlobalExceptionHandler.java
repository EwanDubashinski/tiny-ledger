package com.example.tiny_ledger.controller;

import com.example.tiny_ledger.exception.InsufficientFundsException;
import com.example.tiny_ledger.exception.InvalidAmountException;
import com.example.tiny_ledger.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFunds(InsufficientFundsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponse()
                        .code("INSUFFICIENT_FUNDS")
                        .message(ex.getMessage())
                );
    }
    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAmount(InvalidAmountException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponse()
                        .code("INVALID_AMOUNT")
                        .message(ex.getMessage())
                );
    }
}
