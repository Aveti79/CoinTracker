package com.aveti.CoinTracker.controller;

import com.aveti.CoinTracker.logic.CoinGeckoApiService;
import com.aveti.CoinTracker.model.Currency;
import com.aveti.CoinTracker.model.CurrencyList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CoinGeckoApiController {

    private final CoinGeckoApiService apiService;
    private final RestTemplate restTemplate = new RestTemplate();
    public static final String baseApiUrl = "https://api.coingecko.com/api/v3";

    CoinGeckoApiController(final CoinGeckoApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/coins-list/update")
    public List<Currency> getCoinsList() {
        String resourceUrl = baseApiUrl + "/coins/list";
        CurrencyList currencyList = restTemplate.getForObject(resourceUrl, CurrencyList.class);
        if (currencyList != null) {
            apiService.updateCoinsList(currencyList);
        }
        return apiService.getCoinsListFromDatabase();
    }

    @GetMapping("/coins-details/get-details")
    public void getCoinsDetails() {
        apiService.getCoinsDetailsFromAPI();
    }

    @GetMapping("/coins-details/update-details")
    public void updateCoinsDetails() {
        apiService.updateCoinsDetailsFromAPI();
    }
}
