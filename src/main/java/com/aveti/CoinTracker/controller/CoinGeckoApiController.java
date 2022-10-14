package com.aveti.CoinTracker.controller;

import com.aveti.CoinTracker.logic.CoinGeckoApiService;
import com.aveti.CoinTracker.logic.CurrencyService;
import com.aveti.CoinTracker.logic.TransactionService;
import com.aveti.CoinTracker.model.Coin;
import com.aveti.CoinTracker.model.CoinList;
import com.aveti.CoinTracker.model.CoinPrice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class CoinGeckoApiController {

    private CurrencyService currencyService;
    private TransactionService transactionService;
    private CoinGeckoApiService apiService;
    private RestTemplate restTemplate = new RestTemplate();
    public static final String baseApiUrl = "https://api.coingecko.com/api/v3";

    CoinGeckoApiController(final CurrencyService currencyService, final TransactionService transactionService, final CoinGeckoApiService apiService) {
        this.currencyService = currencyService;
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
    public List<Coin> getCoinsList() {
        String resourceUrl = baseApiUrl + "/coins/list";
        CoinList coinList = restTemplate.getForObject(resourceUrl,CoinList.class);
        if (coinList != null) {
            apiService.updateCoinsList(coinList);
        }
        return apiService.getCoinList();
    }

    @GetMapping("/coins")
    public List<String> getDetailedCoinsList() {

        List<String> coins = transactionService.findAllCoinsUsedInTransactions();

        String resourceUrl = baseApiUrl + "/";
        return coins;
    }

}
