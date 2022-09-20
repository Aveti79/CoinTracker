package com.aveti.CoinTracker.logic;

import com.aveti.CoinTracker.model.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class GainsTableService {
    private TransactionRepository transactionRepository;

    GainsTableService(final TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


}
