package com.aveti.CoinTracker.logic;

import com.aveti.CoinTracker.model.Currency;
import com.aveti.CoinTracker.model.Transaction;
import com.aveti.CoinTracker.model.projection.TransactionReadModel;
import com.aveti.CoinTracker.model.projection.TransactionWriteModel;
import com.aveti.CoinTracker.model.repository.CurrencyRepository;
import com.aveti.CoinTracker.model.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CurrencyRepository currencyRepository;
    private final CoinGeckoApiService apiService;
    private final CoinDetailsService detailsService;

    public TransactionService(final TransactionRepository transactionRepository,
                              final CurrencyRepository currencyRepository,
                              final CoinGeckoApiService apiService,
                              final CoinDetailsService detailsService) {
        this.transactionRepository = transactionRepository;
        this.currencyRepository = currencyRepository;
        this.apiService = apiService;
        this.detailsService = detailsService;
    }

    public Transaction createTransaction(TransactionWriteModel transactionToAdd) {
        Currency buyCurrency = getCurrencyFromString(transactionToAdd.getBuyCurrency());
        Currency sellCurrency = getCurrencyFromString(transactionToAdd.getSellCurrency());
        Currency feeCurrency = getCurrencyFromString(transactionToAdd.getFeeCurrency());
        if (sellCurrency != null && !sellCurrency.getType().equals("FIAT")) {
            transactionToAdd.setSellValueInUsd(
                    transactionToAdd.getSellAmount() * apiService.getCoinHistoricalPrice(
                            sellCurrency.getId(),
                            transactionToAdd.getTransactionTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    )
            );
        } else {
            transactionToAdd.setSellValueInUsd(transactionToAdd.getSellAmount());
        }
        Transaction result = transactionToAdd.toTransaction(buyCurrency, sellCurrency, feeCurrency);
        transactionRepository.save(result);
        return result;
    }

    private Currency getCurrencyFromString(String currencyId) {
        if (!currencyId.isBlank()) {
            return currencyRepository.findById(currencyId)
                    .orElseThrow(() -> new IllegalArgumentException("Currency not found in database"));
        }
        return null;
    }

    public void updateSellValuesInUsd() {
        List<Transaction> transactions = transactionRepository.findAll().stream()
                .filter(transaction -> transaction.getSellValueInUsd() == 0.0)
                .toList();

        for (Transaction trade : transactions) {
            if (!trade.getSellCurrency().getType().equals("FIAT")) {
                trade.setSellValueInUsd(
                        trade.getSellAmount() * apiService.getCoinHistoricalPrice(
                                trade.getSellCurrency().getId(),
                                trade.getTransactionTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                        )
                );
            } else if (trade.getSellCurrency().getType().equals("FIAT") && !trade.getSellCurrency().getId().equals("usd")) {
                trade.setSellValueInUsd(trade.getSellAmount() * apiService.convertFiatToFiat(trade.getSellCurrency().getId(), trade.getTransactionTime()));
            } else {
                trade.setSellValueInUsd(trade.getSellAmount());
            }
        }
        transactionRepository.saveAll(transactions);
    }

    public void updateBuyValuesInUsd() {
        List<Transaction> transactions = transactionRepository.findAll()
                .stream()
                .filter(transaction -> transaction.getBuyValueInUsd() == 0.0)
                .toList();

        for (Transaction trade : transactions) {
            if (!trade.getBuyCurrency().getType().equals("FIAT")) {
                trade.setBuyValueInUsd(
                        trade.getBuyAmount() * apiService.getCoinHistoricalPrice(
                                trade.getBuyCurrency().getId(),
                                trade.getTransactionTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                        )
                );
            } else if (trade.getBuyCurrency().getType().equals("FIAT") && !trade.getBuyCurrency().getId().equals("usd")) {
                trade.setBuyValueInUsd(trade.getBuyAmount() * apiService.convertFiatToFiat(trade.getBuyCurrency().getId(), trade.getTransactionTime()));
            } else {
                trade.setBuyValueInUsd(trade.getBuyAmount());
            }
        }
        transactionRepository.saveAll(transactions);
    }

    public List<Transaction> readAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<TransactionReadModel> readAllTransactionsWithIcons() {
        return transactionRepository.findAll().stream().map(transaction -> {
            var logos = new HashMap<String, String>();
            if (transaction.getBuyCurrency() != null)
                logos.put("buyCurrencyLogo", detailsService.getCurrencyLogo(transaction.getBuyCurrency()));
            if (transaction.getSellCurrency() != null)
                logos.put("sellCurrencyLogo", detailsService.getCurrencyLogo(transaction.getSellCurrency()));
            if (transaction.getFeeCurrency() != null)
                logos.put("feeCurrencyLogo", detailsService.getCurrencyLogo(transaction.getFeeCurrency()));
            return new TransactionReadModel(transaction, logos);
        }).collect(Collectors.toList());
    }

    public Optional<Transaction> findTransactionById(int id) {
        return transactionRepository.findById(id);
    }

    public List<Transaction> findTransactionsByBuyCurrency(String currency) {
        return transactionRepository.findTransactionByBuyCurrencyIdOrderByTransactionTime(currency);
    }

    public List<Transaction> findTransactionsBySellCurrency(String currency) {
        return transactionRepository.findTransactionBySellCurrencyIdOrderByTransactionTime(currency);
    }

}
