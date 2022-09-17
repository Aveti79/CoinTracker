package com.aveti.CoinTracker.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CoinDeserializer.class)
public class Coin {

    private String name;

    private double price;

    private double priceChange24h;

    public Coin() {
    }

    public Coin(String name, double price, double priceChange24h) {
        this.name = name;
        this.price = price;
        this.priceChange24h = priceChange24h;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public double getPriceChange24h() {
        return priceChange24h;
    }

    public void setPriceChange24h(final double priceChange24h) {
        this.priceChange24h = priceChange24h;
    }
}
