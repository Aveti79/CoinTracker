package com.aveti.CoinTracker.controller;

import com.aveti.CoinTracker.logic.TransactionService;
import com.aveti.CoinTracker.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transaction")
public class  TransactionController {

    private final TransactionService service;

    TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/list")
    List<Transaction> getTransactionsList() {
        return service.readAllTransactions();
    }

    @GetMapping("/{id}")
    Optional<Transaction> getTransactionById(@PathVariable int id) {
        return service.findTransactionById(id);
    }

    @GetMapping("/b&{currency}")
    List<Transaction> getTransactionListOfBuyCurrency(@PathVariable String currency) {
        return service.findTransactionsByBuyCurrency(currency);
    }

    @GetMapping("/s&{currency}")
    List<Transaction> getTransactionListOfSellCurrency(@PathVariable String currency) {
        return service.findTransactionsBySellCurrency(currency);
    }

    @PostMapping("/add")
    ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transactionToAdd) {
        Transaction result = service.createTransaction(transactionToAdd);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }
}
