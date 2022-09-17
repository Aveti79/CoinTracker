package com.aveti.CoinTracker.controller;

import com.aveti.CoinTracker.model.Coin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CoinGeckoApiController {

    RestTemplate restTemplate = new RestTemplate();

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

}
