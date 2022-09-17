package com.aveti.CoinTracker.logic;

import com.aveti.CoinTracker.model.Transaction;
import com.aveti.CoinTracker.model.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionService(final TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(final Transaction transactionToAdd) {
        transactionRepository.save(transactionToAdd);
        return transactionToAdd;
    }

    public List<Transaction> readAllTransactions() {
        return transactionRepository.findAll();
    }
}
