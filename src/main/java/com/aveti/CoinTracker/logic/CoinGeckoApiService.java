package com.aveti.CoinTracker.logic;

import com.aveti.CoinTracker.model.CoinDetails;
import com.aveti.CoinTracker.model.CoinPrice;
import com.aveti.CoinTracker.model.Currency;
import com.aveti.CoinTracker.model.CurrencyList;
import com.aveti.CoinTracker.model.repository.CoinDetailsRepository;
import com.aveti.CoinTracker.model.repository.CurrencyRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.aveti.CoinTracker.controller.CoinGeckoApiController.baseApiUrl;

@Service
public class CoinGeckoApiService {

    private final CurrencyRepository currencyRepository;
    private final CoinDetailsRepository coinDetailsRepository;
    private final RestTemplate restTemplate;

    CoinGeckoApiService(final CurrencyRepository currencyRepository, final CoinDetailsRepository coinDetailsRepository, final RestTemplate restTemplate) {
        this.currencyRepository = currencyRepository;
        this.coinDetailsRepository = coinDetailsRepository;
        this.restTemplate = restTemplate;
    }

    public void updateCoinsList(CurrencyList list) {
        currencyRepository.saveAll(list.getCoinList());
        currencyRepository.saveAll(Currency.getAllSupportedFiat());
    }

    public List<Currency> getCoinsListFromDatabase() {
        return currencyRepository.findAll();
    }

    public CoinPrice getCoinPriceInfo(String coin) {
        if (!currencyRepository.existsById(coin)) {
            throw new IllegalArgumentException("Currency not found in database");
        }
        String requestUrl = baseApiUrl + "/simple/price?ids=" + coin + "&vs_currencies=usd&include_24hr_change=true";

        return Optional.ofNullable(restTemplate.getForObject(requestUrl, CoinPrice.class))
                .orElseThrow(() -> new NullPointerException("No Price found for specified currency."));
    }


    /**
     * This method converts currencies handled by API to US Dollars.
     *
     * @param currency the currency you want to convert.
     * @param transactionTime exchange date for the given currency.
     *
     * @return returns how many USD you will get for 1 unit of given currency on a given date.
     */
    public double convertFiatToFiat(String currency, LocalDateTime transactionTime) {
        List<Double> prices = getCoinHistoricalPriceInManyCurrencies("bitcoin",
                transactionTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                "usd", currency);

        var firstPrice = prices.get(0);
        var secondPrice = prices.get(1);

        return firstPrice / secondPrice;
    }

    public double getCoinHistoricalPrice(String coinId, String transactionTime) {
        return getCoinHistoricalPriceInSpecifiedCurrency(coinId, transactionTime, "usd");
    }

    public double getCoinHistoricalPriceInSpecifiedCurrency(String coinId, String transactionTime, String currencyId) {
        String requestUrl = baseApiUrl + "/coins/" + coinId + "/history?date=" + transactionTime + "&localization=false";
        JsonNode jsonNode = restTemplate.getForObject(requestUrl, JsonNode.class);
        assert jsonNode != null;
        return jsonNode.get("market_data").get("current_price").get(currencyId).asDouble();
    }

    public List<Double> getCoinHistoricalPriceInManyCurrencies(String coinId, String transactionTime, String...targetCurrency) {
        String requestUrl = baseApiUrl + "/coins/" + coinId + "/history?date=" + transactionTime + "&localization=false";
        JsonNode jsonNode = restTemplate.getForObject(requestUrl, JsonNode.class);
        List<Double> result = new ArrayList<>();
        for (String target : targetCurrency) {
            assert jsonNode != null;
            result.add(jsonNode.get("market_data").get("current_price").get(target).asDouble());
        }
        return result;
    }

    public void getCoinsDetailsFromAPI() {
        //Getting info of about 4000 coins from api ordered by market cap descending
        for (int i=1; i<=16; i++) {
            String requestUrl = baseApiUrl + "/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=250&page=" + i +"&sparkline=false";
            CoinDetails[] coinDetails = restTemplate.getForObject(requestUrl, CoinDetails[].class);
            if (coinDetails!=null) {
                coinDetailsRepository.saveAll(Arrays.asList(coinDetails));
            }
        }

    }

    public CoinDetails getSingleCoinDetailsFromAPI(String currencyId) {
            String requestUrl = baseApiUrl + "/coins/markets?vs_currency=usd&ids=" + currencyId + "&order=market_cap_desc&sparkline=false";
            CoinDetails[] coinDetails = restTemplate.getForObject(requestUrl, CoinDetails[].class);
            if (coinDetails!=null && coinDetails.length>0) {
                coinDetailsRepository.saveAll(Arrays.asList(coinDetails));
                return coinDetails[0];
            }
            return null;
    }
}