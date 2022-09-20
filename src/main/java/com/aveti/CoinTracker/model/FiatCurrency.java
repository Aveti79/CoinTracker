package com.aveti.CoinTracker.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fiat_currencies")
public class FiatCurrency extends Currency {

    public FiatCurrency() {
    }

    public FiatCurrency(final String id, final String symbol, final String name) {
        setId(id);
        setSymbol(symbol);
        setName(name);
    }

    public static List<FiatCurrency> getAllSupportedFiat() {
        List<FiatCurrency> fiatList = new ArrayList<>();
        fiatList.add(new FiatCurrency("usd", "USD", "US Dollar"));
        fiatList.add(new FiatCurrency("eur", "EUR", "Euro"));
        fiatList.add(new FiatCurrency("pln", "PLN", "Polish Zloty"));
        fiatList.add(new FiatCurrency("rub", "RUB", "Russian Ruble"));
        fiatList.add(new FiatCurrency("krw", "KRW", "South Korean Won"));
        fiatList.add(new FiatCurrency("cny", "CNY", "Chinese Yuan"));
        fiatList.add(new FiatCurrency("jpy", "JPY", "Japanese Yen"));
        fiatList.add(new FiatCurrency("aud", "AUD", "Australian Dollar"));
        fiatList.add(new FiatCurrency("chf", "CHF", "Swiss Franc"));
        fiatList.add(new FiatCurrency("gbp", "GBP", "British Pound Sterling"));
        fiatList.add(new FiatCurrency("try", "TRY", "Turkish Lira"));
        fiatList.add(new FiatCurrency("uah", "UAH", "Ukrainian Hryvnia"));
        return fiatList;
    }

}
