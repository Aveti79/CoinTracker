package com.aveti.CoinTracker.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "currencies")
public class Currency {

    @Id
    private String id;
    private String symbol;
    private String name;

    public Currency() {
    }

    public Currency(final String id, final String symbol, final String name) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getSymbol().toUpperCase() + " " + getName();
    }

    public static List<Currency> getAllSupportedFiat() {
        List<Currency> fiatList = new ArrayList<>();
        fiatList.add(new Currency("usd", "USD", "US Dollar"));
        fiatList.add(new Currency("eur", "EUR", "Euro"));
        fiatList.add(new Currency("pln", "PLN", "Polish Zloty"));
        fiatList.add(new Currency("rub", "RUB", "Russian Ruble"));
        fiatList.add(new Currency("krw", "KRW", "South Korean Won"));
        fiatList.add(new Currency("cny", "CNY", "Chinese Yuan"));
        fiatList.add(new Currency("jpy", "JPY", "Japanese Yen"));
        fiatList.add(new Currency("aud", "AUD", "Australian Dollar"));
        fiatList.add(new Currency("chf", "CHF", "Swiss Franc"));
        fiatList.add(new Currency("gbp", "GBP", "British Pound Sterling"));
        fiatList.add(new Currency("try", "TRY", "Turkish Lira"));
        fiatList.add(new Currency("uah", "UAH", "Ukrainian Hryvnia"));
        return fiatList;
    }
}
