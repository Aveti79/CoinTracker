package com.aveti.CoinTracker.logic;

import com.aveti.CoinTracker.model.CoinPrice;
import com.aveti.CoinTracker.model.Currency;
import com.aveti.CoinTracker.model.CurrencyList;
import com.aveti.CoinTracker.model.repository.CurrencyRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        currencyRepository.saveAll(Currency.getAllSupportedFiat());
    }

    public List<Currency> getCoinList() {
        return currencyRepository.findAll();
    }

    public CoinPrice getCoinPriceInfo(String coin) {
        if (!currencyRepository.existsById(coin)) {
            throw new IllegalArgumentException("Brak wskazanej kryptowaluty w bazie danych");
        }
        String requestUrl = baseApiUrl + "/simple/price?ids=" + coin + "&vs_currencies=usd&include_24hr_change=true";

        return Optional.ofNullable(restTemplate.getForObject(requestUrl, CoinPrice.class))
                .orElseThrow(() -> new NullPointerException("Wystąpił problem z pobraniem aktualnej ceny."));
    }


    /**
     * This method converts currencies handled by API to US Dollars.
     *
     * @param currency the currency you want to convert.
     * @param transactionTime exchange date for the given currency.
     *
     * @return returns how much of given currency you have to pay for 1 US Dollar on a given date.
     */
    public double convertFiatToFiat(String currency, LocalDateTime transactionTime) {
        List<Double> prices = getHistoricalPriceInManyCurrencies("bitcoin",
                transactionTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                "usd", currency);

        var firstPrice = prices.get(0);
        var secondPrice = prices.get(1);

        return firstPrice / secondPrice;
    }

    public Double getCoinHistoricalPrice(String coinId, String transactionTime) {
        return getCoinHistoricalPriceInSpecifiedCurrency(coinId, transactionTime, "usd");
    }

    public Double getCoinHistoricalPriceInSpecifiedCurrency(String coinId, String transactionTime, String currencyId) {
        String requestUrl = baseApiUrl + "/coins/" + coinId + "/history?date=" + transactionTime + "&localization=false";
        JsonNode jsonNode = restTemplate.getForObject(requestUrl, JsonNode.class);
        assert jsonNode != null;
        return jsonNode.get("market_data").get("current_price").get(currencyId).asDouble();
    }

    public List<Double> getHistoricalPriceInManyCurrencies(String coinId, String transactionTime, String...targetCurrency) {
        String requestUrl = baseApiUrl + "/coins/" + coinId + "/history?date=" + transactionTime + "&localization=false";
        JsonNode jsonNode = restTemplate.getForObject(requestUrl, JsonNode.class);
        List<Double> result = new ArrayList<>();
        for (String target : targetCurrency) {
            assert jsonNode != null;
            result.add(jsonNode.get("market_data").get("current_price").get(target).asDouble());
        }
        return result;
    }
}
