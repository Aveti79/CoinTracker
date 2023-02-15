package com.aveti.CoinTracker.controller;

import com.aveti.CoinTracker.logic.TransactionService;
import com.aveti.CoinTracker.model.Currency;
import com.aveti.CoinTracker.model.Transaction;
import com.aveti.CoinTracker.model.projection.TransactionWriteModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
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

    @GetMapping("/buy/{currency}")
    List<Transaction> getTransactionsListOfBuyCurrency(@PathVariable String currency) {
        return service.findTransactionsByBuyCurrency(currency);
    }

    @GetMapping("/sell/{currency}")
    List<Transaction> getTransactionsListOfSellCurrency(@PathVariable String currency) {
        return service.findTransactionsBySellCurrency(currency);
    }

    @PostMapping("/add")
    ResponseEntity<Transaction> addTransaction(@RequestBody TransactionWriteModel transactionToAdd) {
        Transaction result = service.createTransaction(transactionToAdd);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    //Development
    @GetMapping("/update")
    ResponseEntity<?> updateTransactionsUsdPrices() {
        service.updateBuyValuesInUsd();
        service.updateSellValuesInUsd();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
