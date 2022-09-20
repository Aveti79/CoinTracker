package com.aveti.CoinTracker.controller;

import com.aveti.CoinTracker.logic.CoinGeckoApiService;
import com.aveti.CoinTracker.logic.TransactionService;
import com.aveti.CoinTracker.model.Coin;
import com.aveti.CoinTracker.model.CoinList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class CoinGeckoApiController {

    private TransactionService transactionService;
    private CoinGeckoApiService apiService;
    RestTemplate restTemplate = new RestTemplate();
    public static final String baseApiUrl = "https://api.coingecko.com/api/v3";

    CoinGeckoApiController(final TransactionService transactionService, final CoinGeckoApiService apiService) {
        this.transactionService = transactionService;
        this.apiService = apiService;
    }

    @GetMapping("/coinPrice")
    Coin getCoinPrice() {
        String resourceUrl = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd&include_24hr_change=true";

        ResponseEntity<Coin> response = restTemplate.getForEntity(resourceUrl,Coin.class);
        //String resp = response.getBody();
        //System.out.println(resp);

        //Coin coin = restTemplate.getForObject(resourceUrl, Coin.class);

        //System.out.println(coin.getName() + ", " + coin.getPrice() + ", " + coin.getPriceChange24h());

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
