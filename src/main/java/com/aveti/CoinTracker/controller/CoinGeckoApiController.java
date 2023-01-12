package com.aveti.CoinTracker.controller;

import com.aveti.CoinTracker.logic.CoinGeckoApiService;
import com.aveti.CoinTracker.logic.TransactionService;
import com.aveti.CoinTracker.model.CurrencyList;
import com.aveti.CoinTracker.model.CoinPrice;
import com.aveti.CoinTracker.model.Currency;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class CoinGeckoApiController {

    private final TransactionService transactionService;
    private final CoinGeckoApiService apiService;
    private RestTemplate restTemplate = new RestTemplate();
    public static final String baseApiUrl = "https://api.coingecko.com/api/v3";

    CoinGeckoApiController(final TransactionService transactionService, final CoinGeckoApiService apiService) {
        this.transactionService = transactionService;
        this.apiService = apiService;
    }

    @GetMapping("/coinPrice")
    CoinPrice getCoinPrice() {
        String resourceUrl = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd&include_24hr_change=true";

        ResponseEntity<CoinPrice> response = restTemplate.getForEntity(resourceUrl,CoinPrice.class);
        return response.getBody();
    }

    @GetMapping("/list")
    public List<Currency> getCoinsList() {
        String resourceUrl = baseApiUrl + "/coins/list";
        CurrencyList currencyList = restTemplate.getForObject(resourceUrl, CurrencyList.class);
        if (currencyList != null) {
            apiService.updateCoinsList(currencyList);
        }
        return apiService.getCoinList();
    }

    @GetMapping("/coins")
    public List<Currency> getDetailedCoinsList() {

        List<Currency> coins = transactionService.findAllCoinsUsedInTransactions();

        String resourceUrl = baseApiUrl + "/";
        return coins;
    }

    @GetMapping("/coin_history_price")
    public Double getHistoryCoinPrice() {
        return apiService.getCoinHistoricalPrice("bitcoin","27-09-2021");
    }
}
