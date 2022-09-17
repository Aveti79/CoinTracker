package com.aveti.CoinTracker.controller;

import com.aveti.CoinTracker.logic.TransactionService;
import com.aveti.CoinTracker.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController("/transaction")
public class TransactionController {

    private final TransactionService service;

    TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/list")
    List<Transaction> getTransactionsList() {
        return service.readAllTransactions();
    }

    @PostMapping("/add")
    ResponseEntity<Transaction> addTransaction(Transaction transactionToAdd) {
        Transaction result = service.createTransaction(transactionToAdd);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }
}
