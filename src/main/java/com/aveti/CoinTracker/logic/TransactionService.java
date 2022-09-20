package com.aveti.CoinTracker.logic;

import com.aveti.CoinTracker.model.Transaction;
import com.aveti.CoinTracker.model.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(final TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Transaction transactionToAdd) {
        transactionRepository.save(transactionToAdd);
        return transactionToAdd;
    }

    public List<Transaction> readAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> findTransactionById(int id) {
        return transactionRepository.findById(id);
    }

    public List<Transaction> findTransactionsByBuyCurrency(String currency) {
        return transactionRepository.findTransactionByBuyCurrency(currency);
    }

    public List<Transaction> findTransactionsBySellCurrency(String currency) {
        return transactionRepository.findTransactionBySellCurrency(currency);
    }

    public List<String> findAllCoinsUsedInTransactions() {
        return transactionRepository.findAll().stream().map(Transaction::getBuyCurrency).collect(Collectors.toList());
    }
}
