package com.aveti.CoinTracker.model;

import com.aveti.CoinTracker.util.CurrencyDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(using = CurrencyDeserializer.class)
public class CurrencyList extends Currency {

    private List<Currency> coinList;

    public CurrencyList() {
        this.coinList = new ArrayList<>();
    }

    public CurrencyList(List<Currency> coinList) {
        this.coinList = coinList;
    }

    public List<Currency> getCoinList() {
        return coinList;
    }

    public void setCoinList(final List<Currency> coinList) {
        this.coinList = coinList;
    }
}
