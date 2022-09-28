package com.aveti.CoinTracker.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CoinPriceDeserializer.class)
public class CoinPrice {

    private String name;

    private double price;

    private double priceChange24h;

    public CoinPrice() {
    }

    CoinPrice(final String name, final double price, final double priceChange24h) {
        this.name = name;
        this.price = price;
        this.priceChange24h = priceChange24h;
    }

    public String getName() {
        return name;
    }

    void setName(final String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    void setPrice(final double price) {
        this.price = price;
    }

    public double getPriceChange24h() {
        return priceChange24h;
    }

    void setPriceChange24h(final double priceChange24h) {
        this.priceChange24h = priceChange24h;
    }
}
