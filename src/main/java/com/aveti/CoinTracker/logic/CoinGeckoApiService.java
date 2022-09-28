package com.aveti.CoinTracker.logic;

import com.aveti.CoinTracker.model.Coin;
import com.aveti.CoinTracker.model.CoinList;
import com.aveti.CoinTracker.model.CoinPrice;
import com.aveti.CoinTracker.model.repository.CoinRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static com.aveti.CoinTracker.controller.CoinGeckoApiController.baseApiUrl;

@Service
public class CoinGeckoApiService {

    private final CoinRepository coinRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    CoinGeckoApiService(final CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    public void updateCoinsList(CoinList list) {
        coinRepository.saveAll(list.getCoinList());
    }

    public List<Coin> getCoinList() {
        return coinRepository.findAll();
    }

    public CoinPrice getCoinPriceInfo(String coin) {
        if (!coinRepository.existsById(coin)) {
            throw new IllegalArgumentException("Brak wskazanej kryptowaluty w basie danych");
        }
        String requestUrl = baseApiUrl + "/simple/price?ids=" + coin + "&vs_currencies=usd&include_24hr_change=true";

        return Optional.ofNullable(restTemplate.getForObject(requestUrl, CoinPrice.class))
                .orElseThrow(() -> new NullPointerException("Wystąpił problem z pobraniem aktualnej ceny."));
    }
}
