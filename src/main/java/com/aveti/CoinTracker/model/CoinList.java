package com.aveti.CoinTracker.model;

import com.aveti.CoinTracker.util.CoinDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(using = CoinDeserializer.class)
public class CoinList extends Coin {

    private List<Coin> coinList;

    public CoinList() {
        this.coinList = new ArrayList<>();
    }

    public CoinList(List<Coin> coinList) {
        this.coinList = coinList;
    }

    public List<Coin> getCoinList() {
        return coinList;
    }

    public void setCoinList(final List<Coin> coinList) {
        this.coinList = coinList;
    }
}
