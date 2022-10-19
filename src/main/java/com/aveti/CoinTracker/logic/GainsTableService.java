package com.aveti.CoinTracker.logic;

import com.aveti.CoinTracker.model.GainTableRow;
import com.aveti.CoinTracker.model.repository.CoinRepository;
import com.aveti.CoinTracker.model.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GainsTableService {

    private final TransactionRepository transactionRepository;
    private final CoinRepository coinRepository;
    private final CoinGeckoApiService apiService;

    GainsTableService(final TransactionRepository transactionRepository, final CoinRepository coinRepository, final CoinGeckoApiService apiService) {
        this.transactionRepository = transactionRepository;
        this.coinRepository = coinRepository;
        this.apiService = apiService;
    }

    public List<GainTableRow> getTableRows() {
        List<String> currencyList = transactionRepository.findDistinctBuyCurrencies();
        List<GainTableRow> result = new ArrayList<>();
        for (String currency : currencyList) {
            result.add(createTableRow(currency));
        }
        return result;
    }

    public GainTableRow createTableRow(String currency) {
        GainTableRow result = new GainTableRow();
        result.setCurrency(coinRepository.findById(currency));
        result.setAmount(transactionRepository.getSumOfBuyAmount(currency));
        result.setAverageBuyPrice(transactionRepository.getUnitCostOfCurrency(currency));
        result.setSummaryPrice(transactionRepository.getSumOfSellAmount(currency));
        result.setActualPrice(apiService.getCoinPriceInfo(currency).getPrice());
        result.setPriceChange24h(apiService.getCoinPriceInfo(currency).getPriceChange24h());
        result.setActualValue(calculateActualValue(result.getActualPrice(), result.getAmount()));
        result.setPercentageValueChange(calculateValueChange(
                transactionRepository.getSumOfSellAmount(currency),
                result.getActualValue())
        );
        result.setRealizedGains(0.0);
        result.setUnrealizedGains(0.0);
        return result;
    }

    Double calculateActualValue(double amount, double actualPrice) {
        return amount*actualPrice;
    }

    Double calculateValueChange(double sumOfSellAmount, double actualValue) {

        return (actualValue/sumOfSellAmount)-1;
    }

}
