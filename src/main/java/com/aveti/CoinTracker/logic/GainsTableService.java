package com.aveti.CoinTracker.logic;

import com.aveti.CoinTracker.model.CoinPrice;
import com.aveti.CoinTracker.model.Currency;
import com.aveti.CoinTracker.model.GainTableRow;
import com.aveti.CoinTracker.model.Transaction;
import com.aveti.CoinTracker.model.repository.CurrencyRepository;
import com.aveti.CoinTracker.model.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GainsTableService {

    private final TransactionRepository transactionRepository;
    private final CurrencyRepository currencyRepository;
    private final CoinGeckoApiService apiService;

    GainsTableService(final TransactionRepository transactionRepository, final CurrencyRepository currencyRepository, final CoinGeckoApiService apiService) {
        this.transactionRepository = transactionRepository;
        this.currencyRepository = currencyRepository;
        this.apiService = apiService;
    }

    public List<GainTableRow> getTableRows() {
        List<Currency> currencyList = transactionRepository.findDistinctBuyCurrencies()
                .stream()
                .filter(c -> !c.getType().equals("FIAT"))
                .toList();
        List<GainTableRow> result = new ArrayList<>();
        for (Currency currency : currencyList) {
            result.add(createTableRow(currency.getId()));
        }
        return result;
    }

    //TODO: to test exception case
    public GainTableRow createTableRow(String currency) {

        List<Transaction> buyTransactions = transactionRepository.findTransactionByBuyCurrencyIdOrderByTransactionTime(currency);
        List<Transaction> sellTransactions = transactionRepository.findTransactionBySellCurrencyIdOrderByTransactionTime(currency);
        CoinPrice actualCoinPrice = apiService.getCoinPriceInfo(currency);

        GainTableRow result = new GainTableRow();
        result.setCurrency(currencyRepository.findById(currency)
                .orElseThrow(() -> new NoSuchElementException("Brak waluty w bazie danych")));
        result.setAmount(getTotalCurrencyAmount(currency));
        result.setSummaryPrice(getTotalCost(buyTransactions, sellTransactions));
        result.setAverageBuyPrice(calculateAverageUnitCost(result.getSummaryPrice(), result.getAmount()));
        result.setActualPrice(actualCoinPrice.getPrice());
        result.setPriceChange24h(actualCoinPrice.getPriceChange24h());
        result.setActualValue(calculateActualValue(result.getActualPrice(), result.getAmount()));
        result.setPercentageValueChange(calculateValueChange(
                result.getSummaryPrice(),
                result.getActualValue())
        );
        result.setUnrealizedGains(result.getActualValue() - result.getSummaryPrice());
        result.setRealizedGains(getRealizedGains(buyTransactions,sellTransactions));
        return result;
    }

    Double calculateActualValue(double amount, double actualPrice) {
        return amount * actualPrice;
    }

    public Double calculateValueChange(double sumOfSellAmount, double actualValue) {
        if (sumOfSellAmount <= 0) {
            return 0d;
        }
        return (actualValue / sumOfSellAmount) - 1;
    }

    Double getTotalCurrencyAmount(String currencyId) {
        return transactionRepository.getSumOfBuyAmount(currencyId) - transactionRepository.getSumOfSellAmount(currencyId);
    }

    Double calculateAverageUnitCost(double summaryPrice, double amount) {
        if (amount <= 0) {
            return 0d;
        }
        return summaryPrice/amount;
    }

    Double getTotalCost(List<Transaction> buyTransactions, List<Transaction> sellTransactions) {
        double sellAmount = sellTransactions.stream().mapToDouble(Transaction::getSellAmount).sum();
        double tempBuyAmount;
        List<Double> historicalPrices = new ArrayList<>();

        for (Transaction transaction : buyTransactions) {
            tempBuyAmount = transaction.getBuyAmount();
            if (sellAmount > 0) {
                tempBuyAmount = transaction.getBuyAmount() - sellAmount;
            }
            if (tempBuyAmount < 0) {
                sellAmount = Math.abs(tempBuyAmount);
                continue;
            }
            var unitCost = transaction.getBuyValueInUsd()/transaction.getBuyAmount();
            historicalPrices.add(tempBuyAmount * unitCost);
            sellAmount = 0;
        }

        return historicalPrices.stream().mapToDouble(Double::doubleValue).sum();
    }

    Double getRealizedGains(List<Transaction> buyTransactions, List<Transaction> sellTransactions) {
        // Filter sell transactions from exchange transaction
        List<Transaction> sellTradesFiltered = sellTransactions.stream().filter(transaction -> transaction.getBuyCurrency().getType().equals("FIAT")).toList();
        if (sellTradesFiltered.size()==0) return 0d;
        if (buyTransactions.size()==0) {
            return sellTradesFiltered.stream().mapToDouble(Transaction::getBuyAmount).sum();
        }

        List<Double> realizedGains = new ArrayList<>();
        double tempBuyAmount = buyTransactions.get(0).getBuyAmount();
        double tempSellAmount = sellTradesFiltered.get(0).getSellAmount();

        int buyTradesIndex = 0;
        int sellTradesIndex = 0;

        while (sellTradesFiltered.size() > sellTradesIndex) {
            Transaction buyTradeTP = buyTransactions.get(buyTradesIndex);
            Transaction sellTradeTP = sellTradesFiltered.get(sellTradesIndex);
            double buyUnitCost = buyTradeTP.getSellValueInUsd()/buyTradeTP.getBuyAmount();
            double sellAmount;

            if (tempBuyAmount >= tempSellAmount) {
                if (tempSellAmount>0) {
                    sellAmount = tempSellAmount;
                }
                else {
                    sellAmount = sellTradeTP.getSellAmount();
                }

                double sellEarning = sellTradeTP.getBuyValueInUsd();
                double realizedGain = sellEarning - (buyUnitCost*sellAmount);
                realizedGains.add(realizedGain);

                tempBuyAmount -= sellAmount;
                sellTradesIndex++;

                if (sellTradesFiltered.size()>sellTradesIndex) {
                    tempSellAmount = sellTradesFiltered.get(sellTradesIndex).getSellAmount();
                }
                if (tempBuyAmount==0 && buyTransactions.size()-1>buyTradesIndex) {
                    buyTradesIndex++;
                    tempBuyAmount=buyTransactions.get(buyTradesIndex).getBuyAmount();
                }

            } else {

                if (tempBuyAmount>0) {
                    sellAmount = tempBuyAmount;
                }
                else {
                    sellAmount =  buyTradeTP.getBuyAmount();
                }

                double sellUnitEarn = sellTradeTP.getBuyValueInUsd()/sellTradeTP.getSellAmount();
                double sellEarning = sellAmount*sellUnitEarn;
                double realizedGain = sellEarning - (buyUnitCost*sellAmount);
                realizedGains.add(realizedGain);

                tempSellAmount -= sellAmount;
                buyTradesIndex++;

                if (buyTransactions.size()>buyTradesIndex) {
                    tempBuyAmount = buyTransactions.get(buyTradesIndex).getBuyAmount();
                }
            }
        }

        return realizedGains.stream().mapToDouble(Double::doubleValue).sum();
    }

    public Map<String, Double> getTableRowsSum(final List<GainTableRow> gainTableRows) {
        Map<String, Double> tableRowsSum = Stream.of(
                        new AbstractMap.SimpleImmutableEntry<>("summaryPrice", 0d),
                        new AbstractMap.SimpleImmutableEntry<>("actualValue", 0d),
                        new AbstractMap.SimpleImmutableEntry<>("valueChange", 0d),
                        new AbstractMap.SimpleImmutableEntry<>("unrealizedGains", 0d),
                        new AbstractMap.SimpleImmutableEntry<>("realizedGains", 0d))
                .collect(Collectors.toMap(HashMap.Entry::getKey, Map.Entry::getValue));

        gainTableRows.forEach(gainTableRow -> {
            tableRowsSum.put("summaryPrice", tableRowsSum.get("summaryPrice") + gainTableRow.getSummaryPrice());
            tableRowsSum.put("actualValue", tableRowsSum.get("actualValue") + gainTableRow.getActualValue());
            tableRowsSum.put("valueChange", calculateValueChange(tableRowsSum.get("summaryPrice"), tableRowsSum.get("actualValue")));
            tableRowsSum.put("unrealizedGains", tableRowsSum.get("unrealizedGains") + gainTableRow.getUnrealizedGains());
            tableRowsSum.put("realizedGains", tableRowsSum.get("realizedGains") + gainTableRow.getRealizedGains());
        });
        return tableRowsSum;
    }
}
