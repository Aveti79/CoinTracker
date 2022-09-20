package com.aveti.CoinTracker.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "coins")
public class Coin extends Currency {

    public Coin() {
    }

    public Coin(String id, String symbol, String name) {
        setId(id);
        setSymbol(symbol);
        setName(name);
    }

}
