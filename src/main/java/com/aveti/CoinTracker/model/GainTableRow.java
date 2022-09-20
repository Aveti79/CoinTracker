package com.aveti.CoinTracker.model;

public class GainTableRow {

    private Coin currency;
    private double amount;
    private double averageBuyPrice;
    private double summaryPrice;
    private double actualPrice;
    private double priceChange24h;
    private double actualValue;
    private double percentageValueChane;
    private double unrealizedGains;
    private double realizedGains;

    public Coin getCurrency() {
        return currency;
    }

    public void setCurrency(final Coin currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public double getAverageBuyPrice() {
        return averageBuyPrice;
    }

    public void setAverageBuyPrice(final double averageBuyPrice) {
        this.averageBuyPrice = averageBuyPrice;
    }

    public double getSummaryPrice() {
        return summaryPrice;
    }

    public void setSummaryPrice(final double summaryPrice) {
        this.summaryPrice = summaryPrice;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(final double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public double getPriceChange24h() {
        return priceChange24h;
    }

    public void setPriceChange24h(final double priceChange24h) {
        this.priceChange24h = priceChange24h;
    }

    public double getActualValue() {
        return actualValue;
    }

    public void setActualValue(final double actualValue) {
        this.actualValue = actualValue;
    }

    public double getPercentageValueChane() {
        return percentageValueChane;
    }

    public void setPercentageValueChane(final double percentageValueChane) {
        this.percentageValueChane = percentageValueChane;
    }

    public double getUnrealizedGains() {
        return unrealizedGains;
    }

    public void setUnrealizedGains(final double unrealizedGains) {
        this.unrealizedGains = unrealizedGains;
    }

    public double getRealizedGains() {
        return realizedGains;
    }

    public void setRealizedGains(final double realizedGains) {
        this.realizedGains = realizedGains;
    }
}