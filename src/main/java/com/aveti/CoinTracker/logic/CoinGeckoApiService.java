package com.aveti.CoinTracker.logic;

import com.aveti.CoinTracker.model.CurrencyList;
import com.aveti.CoinTracker.model.CoinPrice;
import com.aveti.CoinTracker.model.Currency;
import com.aveti.CoinTracker.model.repository.CurrencyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static com.aveti.CoinTracker.controller.CoinGeckoApiController.baseApiUrl;

@Service
public class CoinGeckoApiService {

    private final CurrencyRepository currencyRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    CoinGeckoApiService(final CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public void updateCoinsList(CurrencyList list) {
        currencyRepository.saveAll(list.getCoinList());
    }

    public List<Currency> getCoinList() {
        return currencyRepository.findAll();
    }

    public CoinPrice getCoinPriceInfo(String coin) {
        if (!currencyRepository.existsById(coin)) {
            throw new IllegalArgumentException("Brak wskazanej kryptowaluty w basie danych");
        }
        String requestUrl = baseApiUrl + "/simple/price?ids=" + coin + "&vs_currencies=usd&include_24hr_change=true";

        return Optional.ofNullable(restTemplate.getForObject(requestUrl, CoinPrice.class))
                .orElseThrow(() -> new NullPointerException("Wystąpił problem z pobraniem aktualnej ceny."));
    }
}
